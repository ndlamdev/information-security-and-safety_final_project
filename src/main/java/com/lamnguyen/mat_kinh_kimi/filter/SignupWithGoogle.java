package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SignupWithGoogle", value = "/dang_ky_voi_google.jsp")
public class SignupWithGoogle implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        BannerImage signupBanner = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("signupBanner");
        if (signupBanner == null) {
            signupBanner = BannerService.getInstance().getBannerByDescription("%banner%signup%");
            ((HttpServletRequest) request).getSession().setAttribute("signupBanner", signupBanner);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
