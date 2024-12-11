package com.lamnguyen.mat_kinh_kimi.controller.bill_detail;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.CancelBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.SaveEditBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.SeeDetailAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BillDetail", value = "/bill_detail")
public class BillDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "see-detail" -> new SeeDetailAction();
            default -> throw new NullPointerException();
        };

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "save" -> new SaveEditBillAction();
            case "cancel-bill" -> new CancelBillAction();
            default -> throw new NullPointerException();
        };
        action.action(request, response);
    }
}