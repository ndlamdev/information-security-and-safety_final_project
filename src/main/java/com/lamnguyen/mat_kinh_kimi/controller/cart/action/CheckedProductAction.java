package com.lamnguyen.mat_kinh_kimi.controller.cart.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Cart;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.CartService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckedProductAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checked = request.getParameter("checked");
        HttpSession session = request.getSession();
        BillService billService = (BillService) session.getAttribute("bill");
        int productId = 0;
        int modelId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            modelId = Integer.parseInt(request.getParameter("modelId"));
        } catch (NumberFormatException e) {

        }

        if (checked.equalsIgnoreCase("true")) {
            CartService cartService = (CartService) session.getAttribute("cart");
            billService.put(Cart.getKey(productId, modelId), cartService.getProductCart(productId, modelId));
        } else billService.remove(Cart.getKey(productId, modelId));
        JSONObject json = new JSONObject();
        billService.setUpJSON(json);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
