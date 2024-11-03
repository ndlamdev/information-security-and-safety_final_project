package controller.banner_manager;

import controller.Action;
import model.bean.BannerImage;
import model.service.BannerService;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UploadFileOnBannerManagement implements Action {


    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext servletContext = request.getServletContext();
            String pathFile = servletContext.getRealPath("/") + "images/banner_management/banner/";
            File file = new File(pathFile);
            if (!file.exists()) file.mkdirs();
            String fullFilePath, subFilePath;
            for (Part part : request.getParts()) {
                if (part.getContentType() != null) {
                    String description = part.getName();
                    String fileName = getFileName(part);
                    long fileSize = part.getSize();
                    if (fileSize > BannerManagerController.MAX_FILE_SIZE) throw new IOException("File size exceeds the limit of 5 MB.");
                    fullFilePath = pathFile + fileName;
                    subFilePath = fullFilePath.substring(fullFilePath.indexOf("images"), fullFilePath.length());

                    // Lưu tệp tải lên vào thư mục trên server
                    try{
                        part.write(fullFilePath);
                    }catch (IOException e){ }
                    //update db
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

        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error uploading file:: " + e.getMessage());
        }
    }
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "unknown";
    }
}