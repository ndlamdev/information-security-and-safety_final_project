package com.lamnguyen.mat_kinh_kimi.controller.bill_history;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.bill_history.action.ChangeServletBillDetailAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_history.action.GetBillHistoryAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BillHistory", value = "/bill_history")
public class BillHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "get" -> new GetBillHistoryAction();
            case "see-detail" -> new ChangeServletBillDetailAction();
            default -> throw new NullPointerException();
        };

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}