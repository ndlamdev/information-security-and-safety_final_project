package com.lamnguyen.mat_kinh_kimi.controller.buy_now;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.buy_now.action.ChangePageBuyNowAction;
import com.lamnguyen.mat_kinh_kimi.controller.buy_now.action.IncreaseProductBuyNowAction;
import com.lamnguyen.mat_kinh_kimi.controller.buy_now.action.PayBuyNowAction;
import com.lamnguyen.mat_kinh_kimi.controller.buy_now.action.ReduceProductBuyNowAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BuyNowController", value = "/buy_now")
public class BuyNowController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = switch (actionStr) {
            case "buy-now" -> new ChangePageBuyNowAction();
            case "increase" -> new IncreaseProductBuyNowAction();
            case "reduce" -> new ReduceProductBuyNowAction();
            case "pay" -> new PayBuyNowAction();
            default -> throw new NullPointerException();
        };


        action.action(request, response);
    }
}
