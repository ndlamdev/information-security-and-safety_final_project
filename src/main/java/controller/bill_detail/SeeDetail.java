package controller.bill_detail;

import controller.Action;
import model.bean.*;
import model.bean.BillDetail;
import model.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeeDetail implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BillService billService = BillService.getInstance();
        String billIdString = request.getParameter("bill-id");
        int billId = 0;
        try {
            billId = Integer.parseInt(billIdString);
        } catch (NumberFormatException e) {
            Action.error(request, response);
            return;
        }

        Bill bill = billService.getBill(billId);
        if (bill == null) {
            Action.error(request, response);
            return;
        }

        ProductService service = ProductService.getInstance();
        ModelService modelService = ModelService.getInstance();
        ProductCart productCart;
        List<ProductCart> products = new ArrayList<>();
        int productId, modelId, quantity;
        Model model;

        for (BillDetail billDetails : bill.getDetails()) {
            productId = billDetails.getProductId();
            modelId = billDetails.getModelId();
            quantity = billDetails.getQuantity();
            productCart = service.getProductBill(productId);
            if (productCart == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            model = modelService.getModelForCart(modelId);
            if (model == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            productCart.setDiscount(billDetails.getPrice());
            productCart.setQuantity(quantity);
            productCart.setModel(model);
            products.add(productCart);
        }

        request.setAttribute("bill", bill);
        request.setAttribute("products", products);
        request.getRequestDispatcher("chi_tiet_hoa_don.jsp").forward(request, response);
    }
}
