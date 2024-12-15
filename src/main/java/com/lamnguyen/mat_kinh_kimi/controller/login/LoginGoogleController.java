package com.lamnguyen.mat_kinh_kimi.controller.login;

import com.lamnguyen.mat_kinh_kimi.config.google.LoginGoogleHandler;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.model.UserGoogleDto;
import com.lamnguyen.mat_kinh_kimi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginGoogleController", value = "/login_google")
public class LoginGoogleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String accessToken = LoginGoogleHandler.getToken(code);
        UserGoogleDto userGoogleDto = LoginGoogleHandler.getUserInfo(accessToken);

        String email = userGoogleDto.getEmail();
        UserService userService = UserService.getInstance();
        User user;
        if (userService.containsEmail(email)) {
            user = userService.getUser(email);

            if (user == null) {
                request.setAttribute("login_error", "Tài khoản của bạn đã bị khóa!");
                request.getRequestDispatcher("dang_nhap.jsp").forward(request, response);
                return;
            }

            request.getSession().setAttribute("user", user);
            if (user.isAdmin()) {
                response.sendRedirect("admin_pages/dashboard.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } else {
            user = new User();
            user.setEmail(email);
            user.setAvatar(userGoogleDto.getPicture());
            request.setAttribute("user", user);
            request.getRequestDispatcher("dang_ky_voi_google.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}