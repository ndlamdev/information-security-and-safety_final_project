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

public class UpdateBillStatusAction implements Action {
    private final BillStatusService billStatusService = BillStatusService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var billIdString = request.getParameter("bill-id");
        var statusString = request.getParameter("status");
        var note = request.getParameter("note");
        if (note == null || note.isBlank()) {
            response.setStatus(404);
            response.getWriter().println(new JSONObject() {{
                put("message", "Vui lòng nhập ghi chú trạng thái đơn hàng!");
            }});
            return;
        }
        int id;
        BillStatusEnum enumStatus;
        try {
            enumStatus = BillStatusEnum.valueOf(statusString);
            id = Integer.parseInt(billIdString);
        } catch (Exception e) {
            Action.errorAPI(request, response);
            return;
        }

        var status = BillStatus.builder()
                .date(LocalDateTime.now())
                .status(enumStatus.getStatus())
                .billId(id)
                .canEdit(false)
                .describe(note)
                .build();

        billStatusService.insert(status);
        var nextStatus = enumStatus.nextStep();
        var json = new JSONObject();
        json.put("next-status", nextStatus == null ? null : new JSONObject(enumStatus.nextStep()));
        json.put("message", "success");
        json.put("status", new JSONObject(status));
        response.getWriter().println(json);
    }
}
