package com.lamnguyen.mat_kinh_kimi.controller.bill;

import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "billStatus", value = "/set-bills-status-cancel")
public class BillStatusController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        var values = req.getParameter("billIds");
        var arrValues = values.split(",");
        List<Integer> billIds = Arrays.stream(arrValues).map(Integer::parseInt).toList();
        BillStatusService billStatusService = BillStatusService.getInstance();

        billIds.forEach(id -> {
            BillStatus billStatus = new BillStatus();
            billStatus.setBillId(id);
            billStatus.setDate(LocalDateTime.now());
            billStatus.setStatus(BillStatusEnum.CANCEL.getStatus());
            billStatus.setDescribe("Đơn hàng đã bị hủy do yêu cầu của việc hủy khóa");
            billStatus.setCanEdit(false);
            billStatusService.insert(billStatus);
        });

        JSONObject json = new JSONObject();
        json.put("result", true);
        resp.getWriter().println(json);
    }
}
