package controller.banner;

import model.bean.BannerImage;
import model.service.BannerService;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BannerController", value = "/banner")
public class BannerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BannerImage> banners = (List<BannerImage>) request.getSession().getAttribute("banners");
        if(banners == null){
            banners = BannerService.getInstance().getSlideShowImages(); // slide
            request.getSession().setAttribute("banners", banners);
        }

        JSONObject json = new JSONObject();
        json.put("banners", banners);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}