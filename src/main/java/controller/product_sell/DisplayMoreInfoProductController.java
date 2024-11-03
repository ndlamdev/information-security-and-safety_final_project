package controller.product_sell;

import model.bean.BannerImage;
import model.bean.Product;
import model.service.BannerService;
import model.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "DisplayMoreInfoProductController", value = "/more_info_product")
public class DisplayMoreInfoProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> recentlyViewedProducts = (List<Product>) request.getSession().getAttribute("recently-viewed-products");
        recentlyViewedProducts = recentlyViewedProducts == null ? new ArrayList<>() : recentlyViewedProducts;
        ProductService productService = ProductService.getInstance();
        String id = request.getParameter("id");
        Product product = null, recentlyViewedProduct = null;
        try{
            product = productService.getProduct(Integer.parseInt(id));
            recentlyViewedProduct = productService.getRecentlyViewedProduct(Integer.parseInt(id));
        }catch (NumberFormatException e){
            response.sendRedirect("error.jsp");
        }

        if(!recentlyViewedProducts.contains(recentlyViewedProduct)) recentlyViewedProducts.add(recentlyViewedProduct);

        BannerImage logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
        request.setAttribute("logo", logo);

        request.getSession().setAttribute("recently-viewed-products", recentlyViewedProducts);

        request.setAttribute("product", product);
        request.getRequestDispatcher("thong_tin_san_pham.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}