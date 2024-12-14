package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RevertBillAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BillStatusService billStatusService = BillStatusService.getInstance();
        String billIdString = request.getParameter("bill-id");
        int billId;
        try {
            billId = Integer.parseInt(billIdString);
        } catch (NumberFormatException e) {
            response.setStatus(404);
            response.getWriter().println(new JSONObject());
            return;
        }

        billStatusService.revert(billId);
        var lastStatus = billStatusService.getLastStatus(billId).getFirst().getStatus();
        var result = new JSONObject();
        result.put("status", BillStatusEnum.findEnumByStatus(lastStatus).listAfterThisStep());
        result.put("current-status", lastStatus);
        response.getWriter().println(result);
    }
}
