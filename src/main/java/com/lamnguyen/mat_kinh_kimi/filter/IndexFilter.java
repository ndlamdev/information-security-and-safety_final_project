package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;
import com.lamnguyen.mat_kinh_kimi.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

//@WebFilter(filterName = "IndexFilter", value = "/*")
public class IndexFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        List<BannerImage> urlBannerImages = (List<BannerImage>) request.getAttribute("banner-images");
        BannerImage logo = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("logo");
        BannerImage urlBannerPRImages = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("bannerPRImages");
        List<Product> productDiscount = (List<Product>) request.getAttribute("list-product-discount"),
                productProminent = (List<Product>) request.getAttribute("list-product-prominent");
        if (urlBannerImages != null && urlBannerPRImages != null &&
                productDiscount != null && productProminent != null) {
            chain.doFilter(request, response);
            return;
        }

        if (logo == null) {
            logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
            ((HttpServletRequest) request).getSession().setAttribute("logo", logo);
        }

        urlBannerImages = urlBannerImages == null ? BannerService.getInstance().getSlideShowImages() : urlBannerImages; // slide
        urlBannerPRImages = urlBannerPRImages == null ? BannerService.getInstance().getBannerByDescription("%banner%pr%") : urlBannerPRImages;
        productDiscount = productDiscount == null ? ProductService.getInstance().getProductDiscount(12) : productDiscount;
        productProminent = productProminent == null ? ProductService.getInstance().getInfoProminentProductByStart(12) : productProminent;
        request.setAttribute("banner-images", urlBannerImages); // slide
        ((HttpServletRequest) request).getSession().setAttribute("bannerPRImages", urlBannerPRImages); // banner pr
        request.setAttribute("list-product-prominent", productProminent);
        request.setAttribute("list-product-discount", productDiscount);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
