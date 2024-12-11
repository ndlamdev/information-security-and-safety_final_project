package com.lamnguyen.mat_kinh_kimi.controller.product_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.ProductService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LockProductAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int productId = 0;
        String lock = request.getParameter("lock");
        try {
            productId = Integer.parseInt(request.getParameter("product-id"));
        } catch (NumberFormatException e) {
            response.setStatus(404);
        }
        if(lock.equals("1")){
            ProductService.getInstance().unlock(productId);
            json.put("lock", 0);
            json.put("icon", "lock_open");
        }else{
            ProductService.getInstance().lock(productId);
            json.put("lock", 1);
            json.put("icon", "lock");
        }
        response.setStatus(200);
        response.getWriter().print(json.toString());
    }
}
