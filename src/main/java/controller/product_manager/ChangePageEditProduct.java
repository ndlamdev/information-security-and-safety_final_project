package controller.product_manager;

import controller.Action;
import model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePageEditProduct implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("product-id"));
        } catch (NumberFormatException e) {
            response.setStatus(404);
        }

        request.getSession().setAttribute("product-id", productId);
        request.getSession().setAttribute("product-edit", ProductService.getInstance().getProductEdit(productId));
        ProductService.getInstance().lock(productId);
        request.getSession().setAttribute("id-button-cancel", "cancel-edit-product");
        request.getSession().setAttribute("action-submit", "update-product");

        response.setStatus(200);
        response.getWriter().println("");
    }
}
