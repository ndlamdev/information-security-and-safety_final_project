package com.lamnguyen.mat_kinh_kimi.filter;


import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.service.AddressService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FinishedPayingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Bill bill = (Bill) ((HttpServletRequest) request).getSession().getAttribute("billPayed");
        if (bill != null) {
            String addressDetails = AddressService.getInstance().getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                    "<br>" + bill.getAddress();
            request.setAttribute("addressDetails", addressDetails);
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("gio_hang.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}
