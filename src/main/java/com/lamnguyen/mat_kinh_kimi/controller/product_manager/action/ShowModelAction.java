package com.lamnguyen.mat_kinh_kimi.controller.product_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Model;
import com.lamnguyen.mat_kinh_kimi.service.ModelService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class ShowModelAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int modelId = 0;
        try{
           modelId = Integer.parseInt( request.getParameter("com.lamnguyen.mat_kinh_kimi.model-id"));
        }catch (NumberFormatException e){
            response.setStatus(404);
            response.getWriter().println(new JSONObject().toString());
        }

        ModelService modelService = ModelService.getInstance();
        Model model = modelService.getModel(modelId);
        NumberFormat nfNumber = NumberFormat.getNumberInstance(Locale.of("vi", "VN"));
        JSONObject json = new JSONObject();
        json.put("urlProductImage", model.getUrlImage());
        json.put("amountProduct", nfNumber.format(model.getQuantity()));
        json.put("amountProductBought", nfNumber.format(model.getTotalQuantitySold()));
        response.setStatus(200);
        response.getWriter().println(json.toString());
    }
}
