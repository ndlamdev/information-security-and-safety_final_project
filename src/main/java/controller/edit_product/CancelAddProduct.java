package controller.edit_product;

import controller.Action;
import model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelAddProduct implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("product-id"));
        } catch (NumberFormatException e) {
            throw e;
        }

        ProductService.getInstance().delete(productId);
        request.getSession().removeAttribute("product-id");
        request.getSession().removeAttribute("action-submit");
        request.getSession().removeAttribute("id-button-cancel");
    }
}
