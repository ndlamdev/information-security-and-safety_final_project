package controller.banner_manager;

import controller.Action;
import model.bean.BannerImage;
import model.service.BannerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteFileOnBannerManagement implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String slideId = request.getParameter("slideId");
        String subPathFile = request.getParameter("file-path").substring(2);

        BannerImage bannerImage = BannerService.getInstance().getBannerByDescription(slideId);
        BannerService.getInstance().removeSlide(bannerImage);

        String pathFile = request.getServletContext().getRealPath("/") + subPathFile;
        File file = new File(pathFile);
        if(file.exists()) file.delete();

        BannerManagerController.removeAttribute(request);
        response.getWriter().write(slideId);
    }
}
