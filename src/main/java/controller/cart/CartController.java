package controller.cart;

import controller.Action;

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
        Action actionCart = null;
        switch (action) {
            case "add" -> {
                actionCart = new AddProductCart();
            }
            case "increase" -> {
                actionCart = new IncreaseProductCart();
            }
            case "reduce" -> {
                actionCart = new ReduceProductCart();
            }
            case "checked" -> {
                actionCart = new CheckedProduct();
            }
            case "remove" -> {
                actionCart = new RemoveProductCart();
            }
        }

        if (action != null) {
            actionCart.action(request, response);
        } else throw new IOException("");
    }
}