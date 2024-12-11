package com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.BannerManagerController;
import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class AddFileOnBannerManagementAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext servletContext = request.getServletContext();
            String pathFile = servletContext.getRealPath("/") + "images/banner_management/slide/";
            File file = new File(pathFile);
            if (!file.exists()) file.mkdirs();
            String subFilePath;
            Part part = request.getPart("path-file");
            subFilePath = BannerManagerController.getString(pathFile, part);

            int slideId = BannerService.getInstance().nextIdOfSlide();
            BannerImage bannerImage = new BannerImage();
            bannerImage.setId(slideId);
            bannerImage.setUrlImage(subFilePath);
            bannerImage.setDescription("slide-" + slideId);
            BannerService.getInstance().insertSlideShowImages(bannerImage);

            BannerManagerController.removeAttribute(request);
            JSONObject json = new JSONObject();
            json.put("slideId", slideId);
            json.put("url", subFilePath);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error uploading file:: " + e.getMessage());
        }
    }
}
