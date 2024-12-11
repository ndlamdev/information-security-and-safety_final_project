package com.lamnguyen.mat_kinh_kimi.controller.re_send_code_verify;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@WebServlet(name = "ReSendCodeVerifyController", value = "/re_send_code_verify")
public class ReSendCodeVerifyController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        LocalDateTime timeOut = (LocalDateTime) request.getSession().getAttribute("time");
        timeOut = timeOut == null ? LocalDateTime.of(2000, 01, 01, 0, 0, 0) : timeOut;
        if (timeOut.until(LocalDateTime.now(), ChronoUnit.MINUTES) > 10) {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String action = request.getParameter("action");
            UserService userService = UserService.getInstance();

            if (userService.containsEmail(email)) {
                String codeVerify = UUID.randomUUID().toString();
                LocalDateTime time = LocalDateTime.now();
                userService.updateCodeVerify(email, codeVerify, time);
                switch (action) {
                    case "register" -> {
                        String url = request.getRequestURL().toString().replace("re_send_code_verify", "register") + "?action=verify";
                        SendMail.Send(email, "Xác Nhận Đăng Ký", SendMail.getFormRegister(url, fullName, email, BCrypt.hashpw(codeVerify, BCrypt.gensalt(12))));
                    }
                    case "forget-password" -> {
                        String url = request.getRequestURL().toString().replace("re_send_code_verify", "forget_password") + "?action=verify";
                        SendMail.Send(email, "Xác Nhận Thay Đổi Mật Khẩu!", SendMail.getFormForgetPassword(url, email, BCrypt.hashpw(codeVerify, BCrypt.gensalt(12))));
                    }
                }

                session.setAttribute("time", time);
                session.setAttribute("email", email);
                session.setAttribute("message", "Mã xác thực của bạn đã được gửi!");
                response.sendRedirect("xac_thuc.jsp");
                return;
            }

            response.sendRedirect("error.jsp");
            return;
        }

        session.setAttribute("message", "Mã xác thực của bạn đã được gửi!");
        request.getRequestDispatcher("xac_thuc.jsp").forward(request, response);
    }
}
