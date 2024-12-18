package com.lamnguyen.mat_kinh_kimi.controller.bill;

import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BillWillDelete", value = "/bill-will-delete")
public class BillWillDeleteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        var user = (User) req.getSession().getAttribute("user");
        BillService billService = BillService.getInstance();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BillsWillDelete", billService.getBillsWillDelete(user.getId()));
        resp.getWriter().println(jsonObject);
    }
}
