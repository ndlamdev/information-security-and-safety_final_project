package com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class SignBillAction implements Action {
    BillStatusService billStatusService = BillStatusService.getInstance();
    BillService billService = BillService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId;
        try {
            billId = Integer.parseInt(request.getParameter("bill-id"));
        } catch (NumberFormatException e) {
            Action.errorAPI(request, response);
            return;
        }


        var signature = request.getParameter("signature");
        var algorithm = request.getParameter("algorithm");
        var update = billService.updateSignature(billId, Signature.builder().signature(signature).algorithm(algorithm).verify(false).build());
        if (update == 0) {
            Action.errorAPI(request, response);
            return;
        }

        var status = BillStatus.builder()
                .canEdit(true)
                .billId(billId)
                .date(LocalDateTime.now())
                .status(BillStatusEnum.WAIL_CONFIRM.getStatus())
                .describe("Đơn hàng của bạn đang chờ xác nhận!")
                .build();
        billStatusService.insert(status);

        SendMail.Send(BillService.getInstance().getBill(billId).getEmail(), "Ký đơn hàng thành công!", """
                <div style="max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
                    <h2 style="margin-bottom: 20px; color: #333;">Thông báo ký đơn hàng thành công</h2>
                    <p style="margin-bottom: 20px; color: #555;">Xin chào,</p>
                    <p style="margin-bottom: 20px; color: #555;">
                        Chúng tôi xin thông báo rằng đơn hàng của bạn đã được ký xác nhận thành công. Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.
                    </p>
                    <p style="margin-bottom: 20px; color: #555;">
                        Nếu bạn có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email hoặc số điện thoại hỗ trợ.
                    </p>
                    <p style="margin-bottom: 0; color: #555;">Trân trọng,</p>
                    <p style="margin-top: 5px; color: #555; font-weight: bold;">Đội ngũ hỗ trợ khách hàng</p>
                </div>
                """);

        response.getWriter().println(new JSONObject(status));
    }
}
