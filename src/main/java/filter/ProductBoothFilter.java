package filter;

import model.bean.BannerImage;
import model.bean.Product;
import model.service.BannerService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;

public class ProductBoothFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        List<Product> products = (List<Product>) request.getAttribute("products");
        if(products != null) {
            chain.doFilter(request, response);
        }
        request.getRequestDispatcher("product-booth").forward(request, response);
    }
}
