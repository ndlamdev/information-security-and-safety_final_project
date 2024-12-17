package com.lamnguyen.mat_kinh_kimi.controller.buy_now.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Cart;
import com.lamnguyen.mat_kinh_kimi.model.Model;
import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import com.lamnguyen.mat_kinh_kimi.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class ChangePageBuyNowAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
        HttpSession session = request.getSession();
        BillService billService = new BillService();
        ProductService productService = ProductService.getInstance();
        ModelService modelService = ModelService.getInstance();
        ProductDiscountService productDiscountService = ProductDiscountService.getInstance();
        int productId = 0;
        int modelId = 0;
        int quantity = 0;
        try {
            productId = Integer.parseInt(request.getParameter("product-id"));
            modelId = Integer.parseInt(request.getParameter("model-id"));
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
        }

        ProductCart productCart = productService.getProductCart(productId);
        if (productCart == null) return;
        Model model = modelService.getModel(modelId);
        if (model == null) return;
        Cart.setQuantityModel(productId, quantity, productCart, productDiscountService, model);

        billService.put(Cart.getKey(productId, modelId), productCart);
        session.setAttribute("bill-buy-now", billService);

        double totalBill = billService.getTotalBill();
        double totalPriceReduced = billService.getTotalPriceReduced();
        double shippingFee = Double.compare(totalBill, 0) == 0 ? 0 : 20000;

        request.getSession().setAttribute("product", productCart);
        request.setAttribute("product-cart", productCart);
        request.setAttribute("totalBill", nf.format(totalBill));
        request.setAttribute("totalPriceReduced", nf.format(totalPriceReduced));
        request.setAttribute("shippingFee", nf.format(shippingFee));
        request.setAttribute("totalPay", nf.format(totalBill - totalPriceReduced + shippingFee));
        request.getRequestDispatcher("mua_ngay.jsp").forward(request, response);
    }
}
