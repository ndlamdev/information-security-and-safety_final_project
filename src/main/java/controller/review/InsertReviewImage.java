package controller.review;

import controller.Action;
import model.service.ReviewImageService;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class InsertReviewImage implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewId = request.getParameter("review-id");
        Part filePart = request.getPart("input-img-review");
        String fileName = filePart.getSubmittedFileName().replaceAll(" ", "-");
        ServletContext servletContext = request.getServletContext();
        String pathFile = servletContext.getRealPath("/") + "images/review/" + reviewId + "/";
        File file = new File(pathFile);
        if (!file.exists()) file.mkdirs();
        String fullFilePath, subFilePath;
        JSONObject json = new JSONObject();
        for (Part part : request.getParts()) {
            if (part.getContentType() != null) {
                fullFilePath = file.getAbsolutePath() + "/" + fileName;
               try {
                   part.write(file.getAbsolutePath() + "/" + fileName);
               }catch (Exception e) {
                   System.out.println(e);
               }
                subFilePath = fullFilePath.substring(fullFilePath.indexOf("images"), fullFilePath.length());
                int reviewImageId = ReviewImageService.getInstance().insert(reviewId, subFilePath);
                json.put("id", reviewImageId);
                json.put("path", subFilePath);
                response.getWriter().println(json.toString());
                return;
            }
        }
    }
}
