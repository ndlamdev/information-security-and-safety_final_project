/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:35 AM - 14/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.filter;

import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "BillManagerFilter", value = "/admin_pages/quan_ly_hoa_don.jsp")
public class BillManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Bill manager");
        var request = (HttpServletRequest) servletRequest;
        if (request.getAttribute("statuses") == null) request.setAttribute("statuses", BillStatusEnum.getAllStatus());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
