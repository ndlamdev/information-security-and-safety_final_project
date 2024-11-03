package controller.search;

import model.bean.Product;
import model.service.ProductService;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        name = name.replaceAll(" ", "");
        List<Product> products;
        if (name.equals("")) products = new ArrayList<Product>();
        else products = ProductService.getInstance().getSearchProducts(name);
        JSONObject json = new JSONObject();
        json.put("products", products);
        response.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}