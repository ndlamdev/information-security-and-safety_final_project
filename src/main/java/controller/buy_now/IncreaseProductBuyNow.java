package controller.buy_now;

import controller.Action;
import model.bean.Cart;
import model.bean.ProductCart;
import model.service.BillService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class IncreaseProductBuyNow implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
        BillService billService = (BillService) session.getAttribute("bill-buy-now");
        int productId = 0;
        int modelId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            modelId = Integer.parseInt(request.getParameter("modelId"));
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
        }


        ProductCart productCart = billService.getProductCart(productId, modelId);
        productCart.increase(1);

        billService.put(Cart.getKey(productId, modelId), productCart);
        session.setAttribute("bill-buy-now", billService);

        JSONObject json = new JSONObject();
        billService.setUpJSON(json);
        json.put("quantity", productCart.getQuantity());
        json.put("totalPriceProduct", nf.format(productCart.totalPrice()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
