package controller.dash_board;

import controller.Action;
import model.service.ProductService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProduct implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("products", ProductService.getInstance().getProductsIdAndName());
        response.getWriter().println(jsonObject);
    }
}
