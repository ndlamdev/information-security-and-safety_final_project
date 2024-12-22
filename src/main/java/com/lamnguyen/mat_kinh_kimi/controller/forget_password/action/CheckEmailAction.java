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

public class CheckEmailAction implements Action {
    private static final String ERROR_EMAIL_NOT_FOUND = "error-not-found-email-forget-password";
    private static final String ERROR_MESSAGE = "Email không tồn tại trên hệ thống!";
    private static final String REDIRECT_LOGIN_PAGE = "dang_nhap.jsp";
    private static final String REDIRECT_VERIFY_PAGE = "xac_thuc.jsp";
    private static final String EMAIL_PARAMETER = "email";
    private static final String ACTION_VERIFY_PARAM = "action=verify";
    private static final String RESET_PASSWORD_SUBJECT = "Xác Nhận Thay Đổi Mật Khẩu!";
    private static final String FORGET_PASSWORD_ACTION = "forget-password";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute(ERROR_EMAIL_NOT_FOUND, null);

        String email = request.getParameter(EMAIL_PARAMETER);
        if (email == null) {
            response.sendRedirect(REDIRECT_LOGIN_PAGE);
            return;
        }

        UserService userService = UserService.getInstance();
        if (userService.containsEmail(email)) {
            handleValidEmail(request, response, session, email, userService);
        } else {
            handleInvalidEmail(request, response);
        }
    }

    private void handleValidEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session, String email, UserService userService) throws IOException {
        String url = request.getRequestURL().toString() + "?" + ACTION_VERIFY_PARAM;
        String codeVerify = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now();

        userService.updateCodeVerify(email, codeVerify, time);

        String hashedCode = BCrypt.hashpw(codeVerify, BCrypt.gensalt(12));
        SendMail.Send(email, RESET_PASSWORD_SUBJECT, SendMail.getFormForgetPassword(url, email, hashedCode));

        session.setAttribute("email", email);
        session.setAttribute("time", time);
        session.setAttribute("action", FORGET_PASSWORD_ACTION);

        response.sendRedirect(REDIRECT_VERIFY_PAGE);
    }

    private void handleInvalidEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ERROR_EMAIL_NOT_FOUND, ERROR_MESSAGE);
        request.getRequestDispatcher(REDIRECT_LOGIN_PAGE).forward(request, response);
    }
}
