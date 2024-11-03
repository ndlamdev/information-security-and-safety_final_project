package controller.review;

import controller.Action;
import model.bean.ProductReview;
import model.bean.Review;
import model.service.ProductService;
import model.service.ReviewImageService;
import model.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePageEditReview implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdString = request.getParameter("user-id"),
                productIdString = request.getParameter("product-id"),
                modelIdString = request.getParameter("model-id");
        int userId = 0, productId = 0, modelId = 0;
        try {
            userId = Integer.parseInt(userIdString);
            productId = Integer.parseInt(productIdString);
            modelId = Integer.parseInt(modelIdString);
        } catch (NumberFormatException e) {
            Action.error(request, response);
        }

        ReviewService reviewService = ReviewService.getInstance();
        Review review = reviewService.getReview(userId, productId);
        if (review == null) Action.error(request, response);
        ProductReview productReview = ProductService.getInstance().getProductReview(userId, productId, modelId);
        request.setAttribute("review", review);
        request.setAttribute("review-id", review.getId());
        request.setAttribute("product-review", productReview);
        request.setAttribute("action-back", "back");
        request.getRequestDispatcher("danh_gia.jsp").forward(request, response);
    }
}
