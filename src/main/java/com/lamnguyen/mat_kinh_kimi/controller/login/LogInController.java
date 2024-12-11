package com.lamnguyen.mat_kinh_kimi.controller.login;

import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;
import com.lamnguyen.mat_kinh_kimi.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogInController", value = "/login")
public class LogInController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BannerImage logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
        BannerImage loginBanner = BannerService.getInstance().getBannerByDescription("%banner%login%");

        session.setAttribute("logo", logo);
        session.setAttribute("loginBanner", loginBanner);

        request.getRequestDispatcher("dang_nhap.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getMethod().toLowerCase().equals("get")) {
            response.sendRedirect("dang_nhap.jsp");
            return;
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userService = UserService.getInstance();
        User user = userService.login(email, password);
        if (user == null) {
            request.setAttribute("login_error", "Tài khoản hoặc mật khẩu không chính xác!");
            request.getRequestDispatcher("dang_nhap.jsp").forward(request, response);
            return;
        }

        if (user.getVerify() != null) {
            request.setAttribute("verify_error", "Tài khoản của bạn chưa được xác thực.<br> Vui lòng xác thực tài khoản của bạn");
            request.setAttribute("action", "register");
            request.setAttribute("email", user.getEmail());
            request.setAttribute("fullName", user.getFullName());
            request.getRequestDispatcher("dang_nhap.jsp").forward(request, response);
            return;
        }

        session.setAttribute("user", user);
        if (user.isAdmin()) {
            response.sendRedirect("admin_pages/dashboard.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}