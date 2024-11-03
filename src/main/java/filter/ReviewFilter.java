package filter;

import model.bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReviewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String method = servletRequest.getMethod().toLowerCase();
        User user = (User) servletRequest.getSession().getAttribute("user");
        if(method.equals("get") || user == null){
            ((HttpServletResponse)response).sendRedirect("error.jsp");
        }else{
            request.getRequestDispatcher("danh_gia.jsp");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
