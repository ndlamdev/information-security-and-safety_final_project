package filter;

import model.bean.BannerImage;
import model.service.BannerService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SignupFilter", value = "/dang_ky.jsp")
public class SignupFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        BannerImage logo = (BannerImage) ((HttpServletRequest)request).getSession().getAttribute("logo");
        if (logo == null) {
            logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
            ((HttpServletRequest) request).getSession().setAttribute("logo", logo);
        }

        BannerImage signupBanner = (BannerImage) ((HttpServletRequest) request).getSession().getAttribute("signupBanner");
        if (signupBanner == null) {
            signupBanner = BannerService.getInstance().getBannerByDescription("%banner%signup%");
            ((HttpServletRequest) request).getSession().setAttribute("signupBanner", signupBanner);
        }

        chain.doFilter(request, response);
    }
}
