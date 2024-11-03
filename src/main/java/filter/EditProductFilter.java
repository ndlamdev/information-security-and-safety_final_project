package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EditProductFilter", value = "/admin_pages/chinh_sua_san_pham.jsp")
public class EditProductFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if(httpServletRequest.getSession().getAttribute("product-id") == null){
            httpServletResponse.sendRedirect("quan_ly_san_pham.jsp");
        }else{
            chain.doFilter(request, response);
        }
    }
}
