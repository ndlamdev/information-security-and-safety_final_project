package com.lamnguyen.mat_kinh_kimi.controller.register.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterVerifyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String hashCode = request.getParameter("code");
        UserService userService = UserService.getInstance();
        if (!userService.containsEmail(email) || email == null || email.equals("") || hashCode == null || hashCode.equals("")) {
            response.sendRedirect("error.jsp");
            return;
        }
        int result = userService.verify(email, hashCode);
        switch (result) {
            case -1 -> {
                Action.error(request, response);
            }
            case  0 ->{
                LocalDateTime time = LocalDateTime.now();
                String codeVerify = UUID.randomUUID().toString();
                userService.updateCodeVerify(email, codeVerify, time);
                session.setAttribute("time", time);
                session.setAttribute("email", email);
                session.setAttribute("error", "Mã xác nhận của bạn đã hết hạn.<br> Chúng tôi đã gửi một mail xác nhận khác cho bạn");
                response.sendRedirect("xac_thuc.jsp");
            }
            case 1 -> {
                session.removeAttribute("email");
                session.removeAttribute("time");
                session.removeAttribute("error");
                session.setAttribute("message", "Bạn đã đăng ký thành công!");
                response.sendRedirect("dang_nhap.jsp");
            }
        }
    }
}
