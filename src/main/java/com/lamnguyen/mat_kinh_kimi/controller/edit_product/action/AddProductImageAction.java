package com.lamnguyen.mat_kinh_kimi.controller.edit_product.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class AddProductImageAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("product-id");
        Part filePart = request.getPart("image-product");
        String fileName = filePart.getSubmittedFileName().replaceAll(" ", "-");
        ServletContext servletContext = request.getServletContext();
        String url = "images/product/" + productId + "/";
        String pathFile = servletContext.getRealPath("/") + url;
        File file = new File(pathFile);
        if (!file.exists()) file.mkdirs();
        String fullFilePath = pathFile + "/" + fileName;
        for (Part part : request.getParts()) {
            try {
                if (part.getContentType() != null) {
                    part.write(fullFilePath);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        response.getWriter().print(url + fileName);
    }
}
