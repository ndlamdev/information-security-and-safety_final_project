package controller.edit_product;

import controller.Action;
import model.bean.Product;
import model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class UpdateProduct implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdSubmit = (String) request.getSession().getAttribute("submit-product"),
                productIdString = request.getParameter("product-id");
        if (productIdSubmit == null || !productIdSubmit.equals(productIdString)) {
            request.getSession().setAttribute("submit-product", request.getParameter("product-id"));
            Product product = new Product();
            product.setName(request.getParameter("product-name"));
            try {
                product.setId(Integer.parseInt(productIdString));
                product.setCategoryId(Integer.parseInt(request.getParameter("product-category-id")));
                product.setPrice(Double.parseDouble(request.getParameter("product-price")));
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            System.out.println(Arrays.toString(request.getParameterValues("model")));
            System.out.println(Arrays.toString(request.getParameterValues("product-image")));
            System.out.println(Arrays.toString(request.getParameterValues("product-discount")));
            product.setDescribe(request.getParameter("product-describe"));
            product.parseModels(request.getParameterValues("model"));
            product.parseProductImages(request.getParameterValues("product-image"));
            product.setBrandName(request.getParameter("brand"));
            product.setMaterial(request.getParameter("material"));
            product.setType(request.getParameter("type"));
            product.parseProductDiscounts(request.getParameterValues("product-discount"));

            ProductService.getInstance().update(product);
            request.getSession().removeAttribute("product-id");
            request.getSession().removeAttribute("id-button-cancel");
            request.getSession().removeAttribute("product-edit");
            request.getSession().removeAttribute("action-submit");
            request.getSession().removeAttribute("submit-product");
            request.getSession().setAttribute("message", "Cập nhật sản phẩm thành thông!");
            response.getWriter().println("Thêm thành công!");
        }else{
            response.getWriter().print("wait");
        }
    }
}
