/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:08 AM - 20/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ResignBillAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, RuntimeException {
        BillStatusService billStatusService = BillStatusService.getInstance();
        String billIdString = request.getParameter("bill-id");
        int billId;
        try {
            billId = Integer.parseInt(billIdString);
        } catch (NumberFormatException e) {
            Action.errorAPI(request, response);
            return;
        }

        var status = billStatusService.getLastStatus(billId).getFirst();
        var enumStatus = BillStatusEnum.findEnumByStatus(status.getStatus());
        if (enumStatus.getStep() != 0) {
            Action.errorAPI(request, response);
            return;
        }
        BillStatus billStatus = new BillStatus();
        billStatus.setBillId(billId);
        billStatus.setDate(LocalDateTime.now());
        billStatus.setStatus(BillStatusEnum.NOT_SIGN.getStatus());
        billStatus.setDescribe("Yêu cầu ký xác nhận lại đơn hàng!");
        billStatus.setCanEdit(true);
        billStatusService.insert(billStatus);

        response.getWriter().println(new JSONObject(billStatus));
    }
}
