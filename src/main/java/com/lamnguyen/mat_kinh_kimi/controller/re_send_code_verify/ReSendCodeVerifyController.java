package com.lamnguyen.mat_kinh_kimi.controller.re_send_code_verify;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@WebServlet(name = "ReSendCodeVerifyController", value = "/re_send_code_verify")
public class ReSendCodeVerifyController extends HttpServlet {

    private static final long TIMEOUT_MINUTES = 10;
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setupRequestResponse(request, response);

        HttpSession session = request.getSession();
        LocalDateTime lastSentTime = getLastSentTime(session);

        if (isTimeoutExceeded(lastSentTime)) {
            processResendCode(request, response, session);
        } else {
            session.setAttribute("message", "Mã xác thực của bạn đã được gửi!");
            request.getRequestDispatcher("xac_thuc.jsp").forward(request, response);
        }
    }

    /**
     * Cấu hình mã hóa request và response.
     */
    private void setupRequestResponse(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    }

    /**
     * Lấy thời gian gửi mã gần nhất từ session.
     */
    private LocalDateTime getLastSentTime(HttpSession session) {
        LocalDateTime timeOut = (LocalDateTime) session.getAttribute("time");
        return (timeOut != null) ? timeOut : LocalDateTime.of(2000, 1, 1, 0, 0, 0);
    }

    /**
     * Kiểm tra xem đã đủ thời gian gửi lại mã hay chưa.
     */
    private boolean isTimeoutExceeded(LocalDateTime lastSentTime) {
        return lastSentTime.until(LocalDateTime.now(), ChronoUnit.MINUTES) > TIMEOUT_MINUTES;
    }

    /**
     * Xử lý logic gửi lại mã xác thực.
     */
    private void processResendCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String action = request.getParameter("action");

        if (userService.containsEmail(email)) {
            String codeVerify = generateVerificationCode();
            LocalDateTime currentTime = LocalDateTime.now();
            userService.updateCodeVerify(email, codeVerify, currentTime);

            sendVerificationEmail(email, fullName, action, codeVerify, request);

            updateSessionAttributes(session, currentTime, email, "Mã xác thực của bạn đã được gửi!");
            response.sendRedirect("xac_thuc.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Tạo mã xác thực ngẫu nhiên.
     */
    private String generateVerificationCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gửi email xác thực dựa trên hành động cụ thể.
     */
    private void sendVerificationEmail(String email, String fullName, String action, String codeVerify, HttpServletRequest request) {
        String hashedCode = BCrypt.hashpw(codeVerify, BCrypt.gensalt(12));
        String baseUrl = request.getRequestURL().toString();

        switch (action) {
            case "register" -> {
                String url = baseUrl.replace("re_send_code_verify", "register") + "?action=verify";
                SendMail.Send(email, "Xác Nhận Đăng Ký", SendMail.getFormRegister(url, fullName, email, hashedCode));
            }
            case "forget-password" -> {
                String url = baseUrl.replace("re_send_code_verify", "forget_password") + "?action=verify";
                SendMail.Send(email, "Xác Nhận Thay Đổi Mật Khẩu!", SendMail.getFormForgetPassword(url, email, hashedCode));
            }
        }
    }

    /**
     * Cập nhật thông tin vào session.
     */
    private void updateSessionAttributes(HttpSession session, LocalDateTime currentTime, String email, String message) {
        session.setAttribute("time", currentTime);
        session.setAttribute("email", email);
        session.setAttribute("message", message);
    }
}
