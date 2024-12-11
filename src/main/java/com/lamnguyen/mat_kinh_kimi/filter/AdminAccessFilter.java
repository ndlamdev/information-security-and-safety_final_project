package com.lamnguyen.mat_kinh_kimi.filter;


import com.lamnguyen.mat_kinh_kimi.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminAccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("../dang_nhap.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("../dang_nhap.jsp");
            return;
        }
        if (!user.isAdmin()) {
            response.sendRedirect("../index.jsp");
        }

        // Cho phép tiếp tục nếu là admin
        filterChain.doFilter(request, response);
    }
}
