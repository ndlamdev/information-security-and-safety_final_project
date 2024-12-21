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
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
@WebServlet(name = "ReviewController", value = "/review")
public class ReviewController extends HttpServlet {

    private static final Map<String, Action> GET_ACTIONS = new HashMap<>();
    private static final Map<String, Action> POST_ACTIONS = new HashMap<>();

    static {
        // Khởi tạo action cho GET
        GET_ACTIONS.put("load-logo", new LoadBannerForReviewAction());
        GET_ACTIONS.put("get-product-reviews", new GetProductReviewAction());
        GET_ACTIONS.put("write-review", new ChangePageWriteReviewAction());
        GET_ACTIONS.put("edit-review", new ChangePageEditReviewAction());

        // Khởi tạo action cho POST
        POST_ACTIONS.put("add-image", new InsertReviewImageAction());
        POST_ACTIONS.put("update", new UpdateReviewAction());
        POST_ACTIONS.put("remove-review-image", new RemoveReviewImageAction());
        POST_ACTIONS.put("remove-review", new RemoveReviewAction());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response, GET_ACTIONS, "load-logo");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response, POST_ACTIONS, null);
    }

    /**
     * Xử lý yêu cầu GET và POST với action tương ứng.
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response, Map<String, Action> actions, String defaultActionKey)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String actionStr = request.getParameter("action");
        if (actionStr == null && defaultActionKey != null) {
            actionStr = defaultActionKey;
        }

        Action action = actions.get(actionStr);
        if (action == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        action.action(request, response);
    }
}
