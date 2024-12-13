/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:01 PM - 13/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "BaseFilter", value = "/*")
public class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("BaseFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String host = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        var base_url = host + ":" + port + "/" + contextPath;
        request.getServletContext().setAttribute("base_url_https", "https://" + base_url);
        request.getServletContext().setAttribute("base_url_http", "http://" + base_url);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
