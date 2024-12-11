package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.model.Product;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

public class ProductManagerFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        List<Product> products = (List<Product>) request.getAttribute("products");
        if(products != null){
            chain.doFilter(request, response);
            return;
        }

        request.getRequestDispatcher("product_manager").forward(request, response);
    }
}
