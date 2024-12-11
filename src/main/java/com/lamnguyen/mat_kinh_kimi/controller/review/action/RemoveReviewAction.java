package com.lamnguyen.mat_kinh_kimi.controller.review.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveReviewAction implements Action {
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
