package controller.dash_board;

import controller.Action;
import model.service.DashBoardService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NumberListByCategory implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String yearStr = request.getParameter("year");
        int year = 0;
        boolean category = request.getParameter("category").equals("1") ? true : false,
                quarter = request.getParameter("quarter").equals("1") ? true : false;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            response.setStatus(500);
        }

        JSONObject json = new JSONObject();
        json.put("data", DashBoardService.getInstance().numberListByCategory(category, quarter, year));
        response.getWriter().println(json.toString());
    }
}
