package com.lamnguyen.mat_kinh_kimi.controller.forget_password;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.forget_password.action.ChangePasswordAction;
import com.lamnguyen.mat_kinh_kimi.controller.forget_password.action.CheckEmailAction;
import com.lamnguyen.mat_kinh_kimi.controller.forget_password.action.ForgetPasswordVerifyAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ForgetPasswordController", value = "/forget_password")
public class ForgetPasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "verify" -> new ForgetPasswordVerifyAction();
            default -> null;
        };


        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "check_mail" -> new CheckEmailAction();
            case "change_password" -> new ChangePasswordAction();
            default -> null;
        };


        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }
}