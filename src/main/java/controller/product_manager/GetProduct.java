package controller.product_manager;

import controller.Action;
import model.bean.BannerImage;
import model.bean.Product;
import model.service.BannerService;
import model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetProduct implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] brandNames = request.getParameterValues("brand-name"),
                categoryIds = request.getParameterValues("category-id"),
                categoryGroupIds = request.getParameterValues("category-group-id"),
                arrayAvailable = request.getParameterValues("available"),
                pages = request.getParameterValues("page"),
                names = request.getParameterValues("name");
        List<Product> products;
        List<String> listBrandName;
        String brandName, requestStr, name;
        ProductService productService;
        int available = 0, page = 1, offset, totalProduct, categoryId = 0, categoryGroupId = -1, totalProductPerPage = 7;
        brandName = brandNames == null ? "" : brandNames[brandNames.length - 1];
        name = names == null ? "" : names[names.length - 1];
        try {
            available = Integer.parseInt(arrayAvailable == null ? "0" : arrayAvailable[arrayAvailable.length - 1]);
            page = Integer.parseInt(pages == null ? "1" : pages[pages.length - 1]);
            categoryId = Integer.parseInt(categoryIds == null ? "-1" : categoryIds[categoryIds.length - 1]);
            categoryGroupId = Integer.parseInt(categoryGroupIds == null ? "-1" : categoryGroupIds[categoryGroupIds.length - 1]);
        } catch (NumberFormatException e) {
        }
        offset = (page - 1) * 7;
        productService = ProductService.getInstance();
        products = productService.getProductForAdmin(name, categoryGroupId, categoryId, brandName, available, totalProductPerPage, offset);
        totalProduct = productService.totalProduct(name, categoryGroupId, categoryId, brandName, available);
        listBrandName = ProductService.getInstance().getBrands();

        requestStr = "product_manager?name=" + name + "&categoryGroupId=" + categoryGroupId + "&categoryId=" + categoryId + "&brand-name=" + brandName + "&available=" + available + "&page=" + page;

        //load logo hehe
        BannerImage logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
        request.setAttribute("logo", logo);

        request.setAttribute("request", requestStr);
        request.setAttribute("products", products);
        request.setAttribute("list-brand-name", listBrandName);
        request.setAttribute("total-product", totalProduct);
        request.setAttribute("page", page);
        request.setAttribute("total-page", getTotalPage(totalProduct, totalProductPerPage));
        request.setAttribute("name", name);
        request.setAttribute("category", getCategory(categoryGroupId, categoryId));
        request.setAttribute("category-id", categoryId);
        request.setAttribute("category-group-id", categoryGroupId);
        request.setAttribute("brand-name", brandName);
        request.setAttribute("brand-name-string", getBrandName(brandName));
        request.setAttribute("available", available);
        request.setAttribute("available-string", getAvailableString(available));
        request.getRequestDispatcher("quan_ly_san_pham.jsp").forward(request, response);
    }

    private String getAvailableString(int available) {
        switch (available) {
            case 0 -> {
                return "Tất cả";
            }
            case 1 -> {
                return "Còn hàng";
            }
            case -1 -> {
                return "Hết hàng";
            }
        }

        return "---------------Trạng thái---------------";
    }

    private String getBrandName(String brandName) {
        if (brandName == null || brandName.isEmpty())
            return "Tất cả";

        return brandName;
    }

    private String getCategory(int categoryGroupId, int categoryId) {
        if (categoryGroupId != -1 && categoryId == -1) {
            return ProductService.MAP_PAGE.get(16 + categoryGroupId);
        }
        if (categoryGroupId == -1 && categoryId != -1) {
            return ProductService.MAP_PAGE.get(categoryId);
        }

        return "Tất cả";
    }

    private int getTotalPage(int totalProduct, int totalProductPerPage) {
        int result = totalProduct / totalProductPerPage;
        return totalProduct % totalProductPerPage == 0 ? result : result + 1;
    }
}
