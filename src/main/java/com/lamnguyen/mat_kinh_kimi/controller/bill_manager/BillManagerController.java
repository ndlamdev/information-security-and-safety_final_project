package com.lamnguyen.mat_kinh_kimi.controller.bill_manager;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.CancelBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.SaveEditBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action.GetBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action.GetBillDetailAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BillManager", value = "/admin_pages/bill_manager")
public class BillManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "get" -> action = new GetBillAction();
            case "see-detail" -> action = new GetBillDetailAction();
        }

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionString = request.getParameter("action");
        Action action = null;
        switch (actionString){
            case "save" -> {
                action = new SaveEditBillAction();
            }
            case "cancel-bill" -> {
                action = new CancelBillAction();
            }
        }

        action.action(request, response);
    }
}