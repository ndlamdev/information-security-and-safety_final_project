package controller.buy_now;

import controller.Action;

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
        Action action = null;
        switch (actionStr) {
            case "buy-now" -> {
                action = new ChangePageBuyNow();
            }
            case "increase" -> {
                action = new IncreaseProductBuyNow();
            }
            case "reduce" -> {
                action = new ReduceProductBuyNow();
            }
            case "pay" -> {
                action = new PayBuyNow();
            }
        }

        if (action != null) {
            action.action(request, response);
        } else throw new IOException("");
    }
}
