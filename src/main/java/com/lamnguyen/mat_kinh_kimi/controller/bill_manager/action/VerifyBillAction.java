/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.service.VerifyService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import com.lamnguyen.mat_kinh_kimi.util.helper.DocumentHelper;
import com.lamnguyen.mat_kinh_kimi.util.mapper.BillMapper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class VerifyBillAction implements Action {
    BillService service = BillService.getInstance();
    VerifyService verifyService = VerifyService.getInstance();
    BillStatusService billStatusService = BillStatusService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            var id = Integer.parseInt(request.getParameter("bill-id"));
            var bill = service.getBill(id);
            var pathFile = DocumentHelper.createBillTempFileText(BillMapper.billDTO(bill, service.getProductInBill(id)), request);
            var signature = BillService.getInstance().findSignature(id);
            var verify = verifyService.verifyBill(bill.getUserId(), signature.getAlgorithm(), signature.getSignature(), pathFile);
            if (!signature.getVerify() && verify) {
                var status = BillStatus.builder()
                        .date(LocalDateTime.now())
                        .status(BillStatusEnum.CONFIRM_SUCCESS.getStatus())
                        .billId(id)
                        .canEdit(false)
                        .describe("Đơn hàng đã được xác nhận")
                        .build();
                service.updateVerify(id, true);
                billStatusService.insert(status);
                response.getWriter().println(new JSONObject() {{
                    put("message", "success");
                    put("status", new JSONObject(status).toString());
                    put("next-status", new JSONObject(BillStatusEnum.CONFIRM_SUCCESS.nextStep()));
                }});
                return;
            }
            new File(pathFile).delete();
            Action.errorAPI(request, response);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            Action.errorAPI(request, response);
        }
    }
}
