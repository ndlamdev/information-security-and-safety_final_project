package controller.review;

import controller.Action;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "ReviewController", value = "/review")
public class ReviewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        actionStr = actionStr == null? "load-logo" : actionStr;
        Action action = null;
        switch (actionStr) {
            case "load-logo" -> action = new LoadBannerForReview();
            case "get-product-reviews" -> action = new GetProductReview();
            case "write-review" -> action = new ChangePageWriteReview();
            case "edit-review" -> action = new ChangePageEditReview();
        }

        if (action == null) {
            response.sendRedirect("error.jsp");
        }

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "add-image" -> action = new InsertReviewImage();
            case "update" -> action = new UpdateReview();
            case "remove-review-image" -> action = new RemoveReviewImage();
            case "remove-review" -> action = new RemoveReview();
        }

        if (action == null) {
            response.sendRedirect("error.jsp");
        }

        action.action(request, response);
    }
}