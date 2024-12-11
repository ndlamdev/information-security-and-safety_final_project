package com.lamnguyen.mat_kinh_kimi.controller.register;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.register.action.RegisterAction;
import com.lamnguyen.mat_kinh_kimi.controller.register.action.RegisterVerifyAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "verify" -> {
                action = new RegisterVerifyAction();
            }
        }
        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "register" -> {
                action = new RegisterAction();
            }
        }
        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }
}