package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.Product;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

public class ProductBoothFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("ProductBoothFilter");
        if (request.getAttribute("products") != null) {
            chain.doFilter(request, response);
            return;
        }
        request.getRequestDispatcher("product-booth").forward(request, response);
    }
}
