package controller.edit_product;

import controller.Action;
import controller.product_manager.GetBrandProduct;
import controller.product_manager.GetMaterialProduct;
import controller.product_manager.GetTypeProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "EditProductManagerController", value = "/admin_pages/edit_product_manager")
public class EditProductManagerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "get-brands" -> {
                action = new GetBrandProduct();
            }
            case "get-materials" -> {
                action = new GetMaterialProduct();
            }
            case "get-types" -> {
                action = new GetTypeProduct();
            }
        }

        if (action == null) {
            Action.error(request, response);
        } else {
            action.action(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "cancel-add-product" -> {
                action = new CancelAddProduct();
            }
            case "cancel-edit-product" -> {
                action = new CancelEditProduct();
            }
            case "delete-product-image" -> {
                action = new DeleteProductImage();
            }
        }

        if (action == null) {
            Action.error(request, response);
        } else {
            action.action(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "add-product" -> {
                action = new AddProduct();
            }
            case "update-product" -> {
                action = new UpdateProduct();
            }
            case "add-product-image" -> {
                action = new AddProductImage();
            }
        }

        if (action == null) {
            Action.error(request, response);
        } else {
            action.action(request, response);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("OPTIONS");
    }
}






