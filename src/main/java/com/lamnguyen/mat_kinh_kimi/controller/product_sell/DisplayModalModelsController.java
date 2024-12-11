package com.lamnguyen.mat_kinh_kimi.controller.product_sell;

import com.lamnguyen.mat_kinh_kimi.model.Model;
import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.service.ModelService;
import com.lamnguyen.mat_kinh_kimi.service.ProductService;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DisplayModalModels", value = "/show_models")
public class DisplayModalModelsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = 0;
        try{
            productId = Integer.parseInt(request.getParameter("id"));
        }catch (NumberFormatException e){

        }

        ModelService mService = ModelService.getInstance();
        ProductService pService = ProductService.getInstance();
        Product product  = pService.getProductIdAndName(productId);
        List<Model> models = mService.getModelsByProductId(productId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("product", JSONObject.wrap(product));
        jsonObject.put("models", models);

        // Gửi dữ liệu JSON về phía client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}