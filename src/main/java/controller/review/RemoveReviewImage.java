package controller.review;

import controller.Action;
import model.service.ReviewImageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class RemoveReviewImage implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewImageIdString = request.getParameter("review-image-id");
        String path = request.getParameter("path");
        new File(path).delete();
        int reviewImageId = 0;
        try {
            reviewImageId = Integer.parseInt(reviewImageIdString);
        }catch (NumberFormatException e) {
            response.setStatus(404);
        }
        ReviewImageService.getInstance().removeImageReview(reviewImageId);
        response.setStatus(200);
        response.getWriter().println("done");
    }
}
