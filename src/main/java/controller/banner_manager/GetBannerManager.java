package controller.banner_manager;

import controller.Action;
import model.bean.BannerImage;
import model.service.BannerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetBannerManager implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BannerImage> urlBannerImages = BannerService.getInstance().getSlideShowImages(); // slide
        BannerImage urlBannerPRImages = BannerService.getInstance().getBannerByDescription("%banner%pr%"); // banner pr
        BannerImage urlBannerLoginImages = BannerService.getInstance().getBannerByDescription("%banner%login%"); // banner login
        BannerImage urlBannerSignupImages = BannerService.getInstance().getBannerByDescription("%banner%signup%"); // banner Signup
        BannerImage urlBannerLogoImages = BannerService.getInstance().getBannerByDescription("%banner%logo%"); // banner logo
        BannerImage urlBannerContactImages = BannerService.getInstance().getBannerByDescription("%banner%contact%");
        BannerImage urlBannerAuthImages = BannerService.getInstance().getBannerByDescription("%banner%auth%");

        urlBannerImages = urlBannerImages == null? new ArrayList<>() : urlBannerImages;
        urlBannerPRImages = urlBannerPRImages == null? new BannerImage() : urlBannerPRImages;
        urlBannerLoginImages = urlBannerLoginImages == null? new BannerImage() : urlBannerLoginImages;
        urlBannerSignupImages = urlBannerSignupImages == null? new BannerImage() : urlBannerSignupImages;
        urlBannerLogoImages = urlBannerLogoImages == null? new BannerImage() : urlBannerLogoImages;
        urlBannerContactImages = urlBannerContactImages == null? new BannerImage() : urlBannerContactImages;
        urlBannerAuthImages = urlBannerAuthImages == null? new BannerImage() : urlBannerAuthImages;

        request.setAttribute("bannerImages", urlBannerImages); // slide
        request.setAttribute("bannerPRImages", urlBannerPRImages); // banner pr
        request.setAttribute("bannerLoginImages", urlBannerLoginImages); // banner login
        request.setAttribute("bannerSignupImages", urlBannerSignupImages); // banner Signup
        request.setAttribute("bannerLogoImages", urlBannerLogoImages); // banner logo
        request.setAttribute("bannerContactImages", urlBannerContactImages);
        request.setAttribute("bannerAuthImages", urlBannerAuthImages);

        request.getRequestDispatcher("quan_ly_banner.jsp").forward(request, response);
    }
}
