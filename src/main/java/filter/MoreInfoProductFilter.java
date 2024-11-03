package filter;

import model.bean.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "MoreInfoProductFilter", value = "/thong_tin_san_pham.jsp")
public class MoreInfoProductFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Product product = (Product) request.getAttribute("product");
        if(product != null){
            chain.doFilter(request, response);
        }

        request.getRequestDispatcher("product-booth").forward(request, response);
    }
}
