package com.lamnguyen.mat_kinh_kimi.controller.cart;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.cart.action.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CartController", value = "/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Action actionCart =
                switch (action) {
                    case "add" -> new AddProductCartAction();
                    case "increase" -> new IncreaseProductCartAction();
                    case "reduce" -> new ReduceProductCartAction();
                    case "checked" -> new CheckedProductAction();
                    case "remove" -> new RemoveProductCartAction();
                    default -> throw new NullPointerException();
                };

        actionCart.action(request, response);
    }
}