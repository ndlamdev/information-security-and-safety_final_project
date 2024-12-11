package com.lamnguyen.mat_kinh_kimi.controller.product_manager;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.product_manager.action.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProductManagerController", value = "/admin_pages/product_manager")
public class ProductManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Action action = null;
        String actionName = request.getParameter("action");
        if (actionName == null) {
            action = new GetProductAction();
            action.action(request, response);
            return;
        }
        switch (actionName) {
            case "band-name" -> {
                action = new GetBrandProductAction();
            }
            case "show-com.lamnguyen.mat_kinh_kimi.model" -> {
                action = new ShowModelAction();
            }
            case "add" -> {
                action = new ChangePageAddProductAction();
            }
            default -> {
                action = new GetProductAction();
            }
        }

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Action action = null;
        String actionName = request.getParameter("action");
        if (actionName == null) actionName = "";
        switch (actionName) {
            case "edit-product" -> {
                action = new ChangePageEditProductAction();
            }case "lock-product" -> {
                action = new LockProductAction();
            }
        }
        if(action == null){
            Action.error(request, response);
        }else{
            action.action(request, response);
        }
    }
}