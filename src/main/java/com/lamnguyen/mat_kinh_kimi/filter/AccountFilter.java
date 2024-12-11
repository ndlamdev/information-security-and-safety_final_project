package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ProfileController", value = "/tai_khoan.jsp")
public class AccountFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        if (user != null){
            request.setAttribute("user",user);
            request.getRequestDispatcher("tai_khoan.jsp").forward(request,response);
       }
        else ((HttpServletResponse) response).sendRedirect("dang_nhap.jsp");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
