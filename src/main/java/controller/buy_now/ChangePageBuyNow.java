package controller.buy_now;

import controller.Action;
import model.bean.BannerImage;
import model.bean.Cart;
import model.bean.Model;
import model.bean.ProductCart;
import model.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class ChangePageBuyNow implements Action {
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
        Double productDiscount = productDiscountService.getPricePercentage(productId);
        productDiscount = Double.compare(productDiscount, 0.0) != 0 ? (1.0 - productDiscount) * productCart.getPrice() : 0.0;
        productCart.setDiscount(productDiscount);
        productCart.setQuantity(quantity);
        productCart.setModel(model);

        billService.put(Cart.getKey(productId, modelId), productCart);
        session.setAttribute("bill-buy-now", billService);

        double totalBill = billService.getTotalBill();
        double totalPriceReduced = billService.getTotalPriceReduced();
        double shippingFee = Double.compare(totalBill, 0) == 0 ? 0 : 20000;

        request.setAttribute("product-cart", productCart);
        request.setAttribute("totalBill", nf.format(totalBill));
        request.setAttribute("totalPriceReduced", nf.format(totalPriceReduced));
        request.setAttribute("shippingFee", nf.format(shippingFee));
        request.setAttribute("totalPay", nf.format(totalBill - totalPriceReduced + shippingFee));
        request.getRequestDispatcher("mua_ngay.jsp").forward(request, response);
    }
}
