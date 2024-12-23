/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:08 AM - 20/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@SuppressWarnings("MalformedFormatString")
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
        var bill = BillService.getInstance().getBill(billId);
        if (enumStatus.getStep() != 0 || bill == null) {
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
        BillService.getInstance().updateSignature(billId, Signature.builder().verify(false).build());
        SendMail.Send(bill.getEmail(), "Yêu cầu ký lại đơn hàng!",
                "<div style=\"max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                + "<h2 style=\"margin-bottom: 20px; color: #333;\">Đơn hàng của bạn đang được yêu cầu kí lại!</h2>"
                + "<p style=\"margin-bottom: 20px; color: #555;\">Đơn hàng của bạn đang gặp một vài vấn đề không thể xác thực được.. Vui lòng kiểm tra lại thông tin và thực hiện ký lại để xác nhận đơn hàng. Nhấn vào nút bên dưới để được chuyển hướng đến trang ký xác nhận.</p>"
                + "<a href=\"http://localhost:8080/mat_kinh_kimi/bill_history?action=see-detail&bill-id=" + billId + "\" style=\"display:block;width:100%;padding:10px;font-size:16px;color:#fff;background-color:#007bff;border:none;border-radius:5px;text-decoration: none;text-align: center;\">Ký lại đơn hàng</a>"
                + "</div>");
        response.getWriter().println(new JSONObject(billStatus));
    }
}
