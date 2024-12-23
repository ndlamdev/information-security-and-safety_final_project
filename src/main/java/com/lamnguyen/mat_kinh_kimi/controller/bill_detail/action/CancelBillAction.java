package com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.AutoSendMailNotifyService;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CancelBillAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BillStatusService billStatusService = BillStatusService.getInstance();
        String billIdString = request.getParameter("bill-id");
        int billId;
        try {
            billId = Integer.parseInt(billIdString);
        } catch (NumberFormatException e) {
            Action.errorAPI(request, response);
            return;
        }

        var lastStatus = BillStatusService.getInstance().getLastStatus(billId);
        if(BillStatusEnum.findEnumByStatus(lastStatus.getFirst().getStatus()).getStep() == -1){
            Action.errorAPI(request, response);
            return;
        }

        BillStatus billStatus = new BillStatus();
        billStatus.setBillId(billId);
        billStatus.setDate(LocalDateTime.now());
        billStatus.setStatus(BillStatusEnum.CANCEL.getStatus());
        billStatus.setDescribe("Hủy bỏ đơn hàng");
        billStatus.setCanEdit(false);
        billStatusService.insert(billStatus);
        AutoSendMailNotifyService.getInstance().removeAutoSendMail(BillService.getInstance().getBill(billId).getEmail(), billId);
        response.getWriter().println(new JSONObject(billStatus));
    }
}
