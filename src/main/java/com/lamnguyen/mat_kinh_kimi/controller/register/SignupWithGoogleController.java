package com.lamnguyen.mat_kinh_kimi.controller.register;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "SignupWithGoogle", value = "/signup_with_google")
public class SignupWithGoogleController extends HttpServlet implements Action {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
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
            request.getRequestDispatcher("dang_ky_voi_google.jsp").forward(request, response);
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
            request.getRequestDispatcher("dang_ky_voi_google.jsp").forward(request, response);
            return;
        }

        user.setFullName(fullname);
        if(userService.signupWithGoogle(user)) user = userService.getUser(user.getEmail());
        session.setAttribute("user", user);
        response.sendRedirect("index.jsp");
    }
}