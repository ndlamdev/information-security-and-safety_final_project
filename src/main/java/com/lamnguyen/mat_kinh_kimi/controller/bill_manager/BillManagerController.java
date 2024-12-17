package com.lamnguyen.mat_kinh_kimi.controller.bill_manager;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.CancelBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action.SaveEditBillAction;
import com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.NoRouteToHostException;

@WebServlet(name = "BillManager", value = "/admin_pages/bill_manager")
public class BillManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "get" -> new GetBillAction();
            case "see-detail" -> new GetBillDetailAction();
            default -> throw new NoRouteToHostException();
        };


        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "save" -> new SaveEditBillAction(true);
            case "cancel-bill" -> new CancelBillAction();
            case "revert-bill" -> new RevertBillAction();
            case "update-bill-status" -> new UpdateBillStatusAction();
            case "verify-bill" -> new VerifyBillAction();
            default -> throw new NoRouteToHostException();
        };

        action.action(request, response);
    }
}