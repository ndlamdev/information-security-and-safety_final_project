package com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.BannerManagerController;
import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteFileOnBannerManagementAction implements Action {
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
