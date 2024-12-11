package com.lamnguyen.mat_kinh_kimi.controller.forget_password.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class ForgetPasswordVerifyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String hashCode = request.getParameter("code");
        UserService userService = UserService.getInstance();
        if (!userService.containsEmail(email)
                || email == null
                || email.equals("")
                || hashCode == null
                || hashCode.equals("")) {
            Action.error(request, response);
            return;
        }

        int result = userService.verify(email, hashCode);
        response.getWriter().println(result);
        switch (result) {
            case -1 -> {
                Action.error(request, response);
            }
            case 0 -> {
                LocalDateTime time = LocalDateTime.now();
                String codeVerify = UUID.randomUUID().toString();
                userService.updateCodeVerify(email, codeVerify, time);
                SendMail.Send(email, "Xác Nhận Thay Đổi Mật Khẩu!", SendMail.getFormForgetPassword("", email, BCrypt.hashpw(codeVerify, BCrypt.gensalt(12))));
                session.setAttribute("email", email);
                session.setAttribute("time", time);
                session.setAttribute("error", "Mã xác nhận của bạn đã hết hạn.<br> Chúng tôi đã gửi một mail xác nhận khác cho bạn");
                response.sendRedirect("xac_thuc.jsp");
            }
            case 1 -> {
                session.setAttribute("email", email);
                response.sendRedirect("quen_mat_khau.jsp");
            }
        }
    }
}