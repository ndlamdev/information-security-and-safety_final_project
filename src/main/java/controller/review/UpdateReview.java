package controller.review;

import controller.Action;
import model.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateReview implements Action {

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewIdString = request.getParameter("review-id");
        String comment = request.getParameter("comment");
        String starString = request.getParameter("star");
        int reviewId = 0, star = 0;
        try {
            reviewId = Integer.parseInt(reviewIdString);
            star = Integer.parseInt(starString);
        }catch (NumberFormatException e) {
            Action.error(request, response);
        }

        ReviewService reviewService = ReviewService.getInstance();
        reviewService.update(reviewId, star, comment);
        request.getRequestDispatcher("tai_khoan.jsp").forward(request, response);
    }
}
