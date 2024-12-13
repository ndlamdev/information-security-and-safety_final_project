package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "VerifyFilter", value = "/xac_thuc.jsp")
public class VerifyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();

        BannerImage auth = (BannerImage) ((HttpServletRequest)request).getSession().getAttribute("authBanner");
        if (auth == null) {
            auth = BannerService.getInstance().getBannerByDescription("%banner%auth%");
            ((HttpServletRequest)request).getSession().setAttribute("authBanner", auth);
        }
        String email = (String) session.getAttribute("email");
        if (email != null) chain.doFilter(request, response);
        else ((HttpServletResponse) response).sendRedirect("index.jsp");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
