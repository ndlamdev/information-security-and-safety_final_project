package controller.edit_product;

import controller.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteProductImage implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String pathFile = request.getServletContext().getRealPath("/") + request.getParameter("path-file");
        File file = new File(pathFile);
        file.delete();
    }
}
