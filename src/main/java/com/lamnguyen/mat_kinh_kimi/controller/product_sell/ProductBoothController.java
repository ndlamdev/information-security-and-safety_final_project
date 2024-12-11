package com.lamnguyen.mat_kinh_kimi.controller.product_sell;

import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductBoothController", value = "/product-booth")
public class ProductBoothController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String queryString = sqlSpace(request);
        ProductService productService = ProductService.getInstance();
        Map<String, Integer> mapInfRoot = productService.getMapInfRoot(queryString);
        Map<String, List<String>> mapFilter = productService.getMapFilter(queryString);
        Map<String, String> mapSort = productService.getMapSort(queryString);
        List<Product> products = productService.getProducts(mapInfRoot, mapFilter, mapSort, 20);
        List<String> materials = productService.getMaterials(),
                types = productService.getTypes(),
                brands = productService.getBrands();
        int totalPages = productService.getTotalPages(mapInfRoot, mapFilter, mapSort);

        int idCategory = mapInfRoot.get("id-category"),
                idCategoryGroup = mapInfRoot.get("id-category-group"),
                page = mapInfRoot.get("page");

        String title = productService.getTitle(idCategoryGroup, idCategory);
        request.setAttribute("products", products);
        request.setAttribute("title", title);
        request.setAttribute("materials", materials);
        request.setAttribute("types", types);
        request.setAttribute("brands", brands);
        request.setAttribute("request", queryString);
        request.setAttribute("total-page", totalPages);
        request.setAttribute("page", page);
        request.setAttribute("mapInfRoot", mapInfRoot);
        request.setAttribute("mapFilter", mapFilter);
        request.setAttribute("totalFilter", total(mapFilter));
        request.setAttribute("mapSort", mapSort);
        request.getRequestDispatcher("gian_hang.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public String sqlSpace(HttpServletRequest request) {
        String query = "id-category-group=";
        String[] pages = request.getParameterValues("page");
        String idCategoryGroup = request.getParameter("id-category-group");
        String idCategory = request.getParameter("id-category");
        query += idCategoryGroup == null ? "0" : idCategoryGroup;
        query += "&id-category=";
        query += idCategory == null ? "0" : idCategory;
        query += "&page=";
        query += pages == null ? "1" : pages[pages.length - 1];
        if (request.getParameter("none") != null) return query;

        String[] arrayFilterBrand = request.getParameterValues("com.lamnguyen.mat_kinh_kimi.filter-brand");
        String[] arrayFilterMaterial = request.getParameterValues("com.lamnguyen.mat_kinh_kimi.filter-material");
        String[] arrayFilterPrice = request.getParameterValues("com.lamnguyen.mat_kinh_kimi.filter-price");
        String[] arrayFilterType = request.getParameterValues("com.lamnguyen.mat_kinh_kimi.filter-type");
        String[] arraySortName = request.getParameterValues("sort-name");
        String[] arraySortPrice = request.getParameterValues("sort-price");
        query += format("com.lamnguyen.mat_kinh_kimi.filter-brand", arrayFilterBrand);
        query += format("com.lamnguyen.mat_kinh_kimi.filter-material", arrayFilterMaterial);
        query += format("com.lamnguyen.mat_kinh_kimi.filter-price", arrayFilterPrice);
        query += format("com.lamnguyen.mat_kinh_kimi.filter-type", arrayFilterType);
        query += format("sort-name", arraySortName);
        query += format("sort-price", arraySortPrice);
        return query;
    }

    public String format(String filter, String[] array) {
        StringBuilder sb = new StringBuilder();
        if (array == null) return sb.toString();
        for (String value : array) {
            int index = sb.indexOf(value);
            if (index == -1) sb.append("&" + filter + "=" + value);
            else {
                int length = 1 + filter.length() + value.length();
                int start = index - (1 + filter.length());
                sb.delete(start, start + length);
            }
        }

        return sb.toString();
    }

    public int total(Map<String, List<String>> map) {
        int total = 0;
        for (List<String> list : map.values()) {
            total += list.size();
        }

        return total;
    }
}