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
import com.lamnguyen.mat_kinh_kimi.service.CartService;
import com.lamnguyen.mat_kinh_kimi.util.enums.HashAlgorithms;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "BaseFilter", value = "/*")
public class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("BaseFilter");

        CartService cart = (CartService) ((HttpServletRequest) request).getSession().getAttribute("cart");
        if (cart == null)
            ((HttpServletRequest) request).getSession().setAttribute("cart", new CartService());


        if (request.getServletContext().getAttribute("base_url_https") == null) {
            var host = request.getServerName();
            var port = request.getServerPort();
            var contextPath = ((HttpServletRequest) request).getContextPath();
            var base_url = host + ":" + port + "/" + contextPath;
            request.getServletContext().setAttribute("base_url_https", "https://" + base_url);
            request.getServletContext().setAttribute("base_url_http", "http://" + base_url);
        }

        if (request.getServletContext().getAttribute("logo") == null)
            request.getServletContext().setAttribute("logo", BannerService.getInstance().getBannerByDescription("%banner%logo%"));


        if (request.getServletContext().getAttribute("signupBanner") == null)
            request.getServletContext().setAttribute("signupBanner", BannerService.getInstance().getBannerByDescription("%banner%signup%"));


        if (request.getServletContext().getAttribute("loginBanner") == null)
            request.getServletContext().setAttribute("loginBanner", BannerService.getInstance().getBannerByDescription("%banner%login%"));


        if (request.getServletContext().getAttribute("contact") == null)
            request.getServletContext().setAttribute("contact", BannerService.getInstance().getBannerByDescription("%banner%contact%"));


        BannerImage urlBannerPRImages = (BannerImage) request.getServletContext().getAttribute("bannerPRImages");
        request.getServletContext().setAttribute("bannerPRImages", urlBannerPRImages);

        if (request.getServletContext().getAttribute("authBanner") == null)
            request.getServletContext().setAttribute("authBanner", BannerService.getInstance().getBannerByDescription("%banner%auth%"));

        if (request.getServletContext().getAttribute("algorithms") == null)
            request.getServletContext().setAttribute("algorithms", HashAlgorithms.ALGORITHMS);

        chain.doFilter(request, response);
    }
}
