package com.lamnguyen.mat_kinh_kimi.controller.bill;

import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "billStatus", value = "/set-bills-status-cancel")
public class BillStatusController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        var values =  req.getParameter("billIds");
        var arrValues = values.split(",");
        List<Integer> billIds = Arrays.stream(arrValues).map(Integer::parseInt).toList();
        BillStatusService billStatusService = BillStatusService.getInstance();
        boolean result = billStatusService.setStatusBillsCancel(billIds);

        JSONObject json = new JSONObject();
        json.put("result", result);
        resp.getWriter().println(json);
    }
}
