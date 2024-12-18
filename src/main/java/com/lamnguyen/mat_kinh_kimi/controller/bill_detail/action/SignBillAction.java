package com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action;

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

        response.getWriter().println(new JSONObject(status));
    }
}
