package com.lamnguyen.mat_kinh_kimi.controller.edit_product;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.edit_product.action.*;
import com.lamnguyen.mat_kinh_kimi.controller.product_manager.action.GetBrandProductAction;
import com.lamnguyen.mat_kinh_kimi.controller.product_manager.action.GetMaterialProductAction;
import com.lamnguyen.mat_kinh_kimi.controller.product_manager.action.GetTypeProductAction;

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
                action = new GetBrandProductAction();
            }
            case "get-materials" -> {
                action = new GetMaterialProductAction();
            }
            case "get-types" -> {
                action = new GetTypeProductAction();
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
                action = new CancelAddProductAction();
            }
            case "cancel-edit-product" -> {
                action = new CancelEditProductAction();
            }
            case "delete-product-image" -> {
                action = new DeleteProductImageAction();
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
                action = new AddProductAction();
            }
            case "update-product" -> {
                action = new UpdateProductAction();
            }
            case "add-product-image" -> {
                action = new AddProductImageAction();
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






