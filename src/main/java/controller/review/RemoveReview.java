package controller.review;

import controller.Action;
import model.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveReview implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewIdString = request.getParameter("review-id");
        int reviewId = 0;
        try {
            reviewId = Integer.parseInt(reviewIdString);
        }catch (NumberFormatException e) {
            Action.error(request, response);
        }

        ReviewService reviewService = ReviewService.getInstance();
        reviewService.removeReview(reviewId);
        response.getWriter().println("done");
    }
}
