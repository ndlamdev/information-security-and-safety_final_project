package com.lamnguyen.mat_kinh_kimi.controller.search;

import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.service.ProductService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setupRequestResponse(request, response);

        String name = getSearchKeyword(request);
        List<Product> products = fetchProducts(name);

        sendJsonResponse(response, products);
    }

    /**
     * Cấu hình mã hóa request và response.
     */
    private void setupRequestResponse(HttpServletRequest request, HttpServletResponse response) {
//        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * Lấy và làm sạch tham số 'name' từ request.
     */
    private String getSearchKeyword(HttpServletRequest request) {
        String name = request.getParameter("name");
        return (name != null) ? name.replaceAll(" ", "") : "";
    }

    /**
     * Lấy danh sách sản phẩm dựa trên từ khóa tìm kiếm.
     */
    private List<Product> fetchProducts(String name) {
        if (name.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu không có từ khóa
        }
        return productService.getSearchProducts(name);
    }

    /**
     * Gửi phản hồi JSON chứa danh sách sản phẩm.
     */
    private void sendJsonResponse(HttpServletResponse response, List<Product> products) throws IOException {
        JSONObject json = new JSONObject();
        json.put("products", products);

        response.setContentType("application/json");
        response.getWriter().println(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported.");
    }
}
