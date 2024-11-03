package filter;


import helper.SendMail;
import model.bean.Bill;
import model.service.AddressService;

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
            String addressDetails = new AddressService().getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                    "<br>" + bill.getAddress();
            request.setAttribute("addressDetails", addressDetails);
            String url = ((HttpServletRequest) request).getRequestURL().toString().replace("thanh_toan_thanh_cong.jsp", "policy_pages/kiem_tra_don_hang.jsp");
            SendMail.SendMailWithImage(bill.getEmail(), "Thanh toán thành công", SendMail.getFormBill(url, bill, addressDetails));
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("gio_hang.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}
