/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.VerifyService;
import com.lamnguyen.mat_kinh_kimi.util.helper.PDFDocumentHelper;
import com.lamnguyen.mat_kinh_kimi.util.mapper.BillMapper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyBillAction implements Action {
    BillService service = BillService.getInstance();
    VerifyService verifyService = VerifyService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        var user = session.getAttribute("user");
        if (user == null) {
            Action.errorAPI(request, response);
            return;
        }

        try {
            var id = Integer.parseInt(request.getAttribute("id").toString());
            var bill = service.findByUserIdAndId(((User) user).getId(), id);
            var pathFile = PDFDocumentHelper.createBillTempFile(BillMapper.billDTO(bill, service.getProductInBill(id)), request);
            if (verifyService.verifyBill(((User) user).getId(), bill.getSignature(), pathFile)) {
                response.getWriter().println(new JSONObject() {{
                    put("message", "success");
                }});
                return;
            }
            Action.errorAPI(request, response);
        } catch (Exception e) {
            Action.errorAPI(request, response);
            return;
        }
    }
}
