package controller.review;

import controller.Action;
import model.bean.Bill;
import model.bean.Product;
import model.bean.ProductReview;
import model.service.ProductService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetProductReview implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdString = request.getParameter("user-id");
        String pageString = request.getParameter("page");
        String haveEvaluated = request.getParameter("have-evaluated");

        int page, userId = 0;
        try{
            userId = Integer.parseInt(userIdString);
            page = Integer.parseInt(pageString);
        }catch (NumberFormatException e) {
            response.setStatus(404);
            response.getWriter().println(new JSONObject());
            return;
        }

        int offset = (page - 1) * 8;
        List<ProductReview> productReviews = null;
        JSONObject json = new JSONObject();
        if(haveEvaluated.toLowerCase().equals("true")) {
            productReviews = ProductService.getInstance().getProductReviewsHaveEvaluated(userId, offset);
            json.put("action", "edit-review");
        }
        else {
            productReviews = ProductService.getInstance().getProductReviewsNotYetRated(userId, offset);
            json.put("action", "write-review");
        }
        json.put("productReviews", productReviews);
        json.put("user-id", userId);
        response.getWriter().println(json.toString());
    }
}
