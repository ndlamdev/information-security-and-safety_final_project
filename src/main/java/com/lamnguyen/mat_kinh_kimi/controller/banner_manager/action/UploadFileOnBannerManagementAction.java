package com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.BannerManagerController;
import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UploadFileOnBannerManagementAction implements Action {


    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext servletContext = request.getServletContext();
            String pathFile = servletContext.getRealPath("/") + "images/banner_management/banner/";
            File file = new File(pathFile);
            if (!file.exists())
                if (!file.mkdirs()) throw new Exception("Create folder fail!");
            String subFilePath;
            for (Part part : request.getParts()) {
                if (part.getContentType() != null) {
                    String description = part.getName();
                    subFilePath = BannerManagerController.getString(pathFile, part);
                    //update com.lamnguyen.mat_kinh_kimi.db
                    BannerImage bannerImage = new BannerImage();
                    bannerImage.setDescription(description);
                    bannerImage.setUrlImage(subFilePath);
                    BannerService.getInstance().uploadBannerImage(bannerImage);

                    BannerManagerController.removeAttribute(request);
                    JSONObject json = new JSONObject();
                    json.put("url", subFilePath);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error uploading file:: " + e.getMessage());
        }
    }
}