package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.BannerImage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;
@WebFilter(filterName = "BannerManagerFilter", value = "/admin_pages/quan_ly_banner.jsp")
public class BannerManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        List<BannerImage> urlBannerImages = (List<BannerImage>) request.getAttribute("bannerImages"); // slide
        BannerImage urlBannerPRImages = (BannerImage) request.getAttribute("bannerPRImages"); // banner pr
        BannerImage urlBannerLoginImages = (BannerImage) request.getAttribute("bannerLoginImages"); // banner login
        BannerImage urlBannerSignupImages = (BannerImage) request.getAttribute("bannerSignupImages"); // banner Signup
        BannerImage urlBannerLogoImages = (BannerImage) request.getAttribute("bannerLogoImages"); // banner logo
        BannerImage urlBannerContactImages = (BannerImage) request.getAttribute("bannerContactImages");
        BannerImage urlBannerAuthImages = (BannerImage) request.getAttribute("bannerAuthImages");

        if (urlBannerImages != null
                && urlBannerPRImages != null
                && urlBannerLoginImages != null
                && urlBannerSignupImages != null
                && urlBannerLogoImages != null
                && urlBannerContactImages != null
                && urlBannerAuthImages != null) {
            chain.doFilter(request, response);
        }else{
            request.getRequestDispatcher("banner_manager").forward(request, response);
        }
    }
}
