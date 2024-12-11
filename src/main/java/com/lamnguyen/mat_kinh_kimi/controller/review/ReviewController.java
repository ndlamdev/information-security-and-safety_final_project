package com.lamnguyen.mat_kinh_kimi.controller.review;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.review.action.*;

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
            case "load-logo" -> action = new LoadBannerForReviewAction();
            case "get-product-reviews" -> action = new GetProductReviewAction();
            case "write-review" -> action = new ChangePageWriteReviewAction();
            case "edit-review" -> action = new ChangePageEditReviewAction();
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
            case "add-image" -> action = new InsertReviewImageAction();
            case "update" -> action = new UpdateReviewAction();
            case "remove-review-image" -> action = new RemoveReviewImageAction();
            case "remove-review" -> action = new RemoveReviewAction();
        }

        if (action == null) {
            response.sendRedirect("error.jsp");
        }

        action.action(request, response);
    }
}