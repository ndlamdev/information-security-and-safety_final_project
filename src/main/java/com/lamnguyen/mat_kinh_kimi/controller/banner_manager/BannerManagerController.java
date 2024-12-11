package com.lamnguyen.mat_kinh_kimi.controller.banner_manager;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action.AddFileOnBannerManagementAction;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action.DeleteFileOnBannerManagementAction;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action.GetBannerManagerAction;
import com.lamnguyen.mat_kinh_kimi.controller.banner_manager.action.UploadFileOnBannerManagementAction;
import com.lamnguyen.mat_kinh_kimi.util.helper.PartHelper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
@WebServlet(name = "BannerManagerController", value = "/admin_pages/banner_manager")
public class BannerManagerController extends HttpServlet {
    public static final int MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = null;
        String actionStr = request.getParameter("action");
        actionStr = actionStr == null ? "banner" : actionStr;

        switch (actionStr) {
            case "banner" -> {
                action = new GetBannerManagerAction();
            }
        }

        if (action == null) {
            response.sendRedirect("error.jsp");
        }

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = null;
        String actionStr = request.getParameter("action");

        switch (actionStr) {
            case "add-file" -> {
                action = new AddFileOnBannerManagementAction();
            }
            case "upload-file" -> {
                action = new UploadFileOnBannerManagementAction();
            }
        }

        if (action == null) {
            response.sendRedirect("error.jsp");
        }

        action.action(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = new DeleteFileOnBannerManagementAction();
        action.action(req, resp);
    }

    public static void removeAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("banners");
        req.getSession().removeAttribute("loginBanner");
        req.getSession().removeAttribute("signupBanner");
        req.getSession().removeAttribute("bannerPRImages");
        req.getSession().removeAttribute("logo");
        req.getSession().removeAttribute("contact");
        req.getSession().removeAttribute("authBanner");
    }

    

    public static String getString(String pathFile, Part part) throws IOException {
        String fullFilePath;
        String subFilePath;
        String fileName = PartHelper.getFileName(part);

        long fileSize = part.getSize();
        if (fileSize > BannerManagerController.MAX_FILE_SIZE)
            throw new IOException("File size exceeds the limit of 5 MB.");

        fullFilePath = pathFile + fileName;
        subFilePath = fullFilePath.substring(fullFilePath.indexOf("images"), fullFilePath.length());

        try {
            part.write(fullFilePath);
        } catch (IOException ignored) {
        }
        return subFilePath;
    }
}