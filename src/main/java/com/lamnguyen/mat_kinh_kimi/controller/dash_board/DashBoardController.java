package com.lamnguyen.mat_kinh_kimi.controller.dash_board;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.dash_board.action.GetProduct;
import com.lamnguyen.mat_kinh_kimi.controller.dash_board.action.NumberListByCategory;
import com.lamnguyen.mat_kinh_kimi.controller.dash_board.action.NumberListByProduct;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashBoardController", value = "/admin_pages/dashboard")
public class DashBoardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "products" -> new GetProduct();
            default -> throw new NullPointerException();
        };
        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "number-list-by-category" -> new NumberListByCategory();
            case "number-list-by-product" -> new NumberListByProduct();
            default -> throw new NullPointerException();
        };

        action.action(request, response);
    }

}