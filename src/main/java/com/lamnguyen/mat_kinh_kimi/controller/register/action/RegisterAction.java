package com.lamnguyen.mat_kinh_kimi.controller.register.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class RegisterAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserService userService = UserService.getInstance();
        String email = request.getParameter("customer[email]");
        String password = request.getParameter("customer[password]");
        String repassword = request.getParameter("customer[repassword]");
        String gender = request.getParameter("customer[gender]");
        String fullname = request.getParameter("customer[fullName]");
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        if (!password.equals(repassword)) {
            request.setAttribute("signup_error_pass", "Password không trùng khớp!");
            request.getRequestDispatcher("dang_ky.jsp").forward(request, response);
            return;
        }

        if (userService.containsEmail(email)) {
            request.setAttribute("signup_error_email", "Emai đã tồn tại trên hệ thống!");
            request.getRequestDispatcher("dang_ky.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setAvatar("images/avatar/default_avatar.png");
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(1);
        user.setSex(gender);
        try {
            user.setBirthDay(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        } catch (Exception e) {
            request.setAttribute("signup_error_birthday", "Lỗi ngày tháng năm sinh!");
            request.getRequestDispatcher("dang_ky.jsp").forward(request, response);
            return;
        }

        user.setFullName(fullname);
        String url = request.getRequestURL().toString() + "?action=verify";
        String codeVerify = UUID.randomUUID().toString();
        userService.signup(user, codeVerify);
        SendMail.Send(user.getEmail(), "Xác Nhận Đăng Ký", SendMail.getFormRegister(url, user.getFullName(), user.getEmail(), BCrypt.hashpw(codeVerify, BCrypt.gensalt(12))));
        session.setAttribute("email", user.getEmail());
        session.setAttribute("action", "register");
        session.setAttribute("fullName", user.getFullName());
        response.sendRedirect("xac_thuc.jsp");
    }
}
