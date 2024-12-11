package com.lamnguyen.mat_kinh_kimi.controller.dash_board.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.DashBoardService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;

public class NumberListByProduct implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");
        String productIdStr = request.getParameter("product-id");
        int year = 0, month = 0, productId = 0;
        try {
            year = Integer.parseInt(yearStr);
            month = Integer.parseInt(monthStr);
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(500);
        }

        JSONObject json = new JSONObject();
        json.put("data", DashBoardService.getInstance().numberListByProduct(productId, month, year));
        json.put("lengthOfMonth", YearMonth.of(year, month).lengthOfMonth());
        response.getWriter().println(json.toString());
    }
}
