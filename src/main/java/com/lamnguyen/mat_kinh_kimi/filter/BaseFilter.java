/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:01 PM - 13/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "BaseFilter", value = "/*")
public class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("BaseFilter");

        if (request.getServletContext().getAttribute("base_url_https") == null) {
            var host = request.getServerName();
            var port = request.getServerPort();
            var contextPath = ((HttpServletRequest) request).getContextPath();
            var base_url = host + ":" + port + "/" + contextPath;
            request.getServletContext().setAttribute("base_url_https", "https://" + base_url);
            request.getServletContext().setAttribute("base_url_http", "http://" + base_url);
        }

        BannerImage logo = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("logo");
        if (logo == null) {
            logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
            ((HttpServletRequest) request).getSession().setAttribute("logo", logo);
        }

        BannerImage signupBanner = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("signupBanner");
        if (signupBanner == null) {
            signupBanner = BannerService.getInstance().getBannerByDescription("%banner%signup%");
            ((HttpServletRequest) request).getSession().setAttribute("signupBanner", signupBanner);
        }

        BannerImage loginBanner = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("loginBanner");
        if (loginBanner == null) {
            loginBanner = BannerService.getInstance().getBannerByDescription("%banner%login%");
            ((HttpServletRequest) request).getSession().setAttribute("loginBanner", loginBanner);
        }

        BannerImage contact = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("contact");
        if (contact == null) {
            contact = BannerService.getInstance().getBannerByDescription("%banner%contact%");
            ((HttpServletRequest) request).getSession().setAttribute("contact", contact);
        }

        BannerImage urlBannerPRImages = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("bannerPRImages");
        ((HttpServletRequest) request).getSession().setAttribute("bannerPRImages", urlBannerPRImages); // banner pr

        BannerImage auth = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("authBanner");
        if (auth == null) {
            auth = BannerService.getInstance().getBannerByDescription("%banner%auth%");
            ((HttpServletRequest) request).getSession().setAttribute("authBanner", auth);
        }

        chain.doFilter(request, response);
    }
}
