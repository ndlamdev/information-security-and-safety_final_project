package com.lamnguyen.mat_kinh_kimi.controller.bill_history.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeServletBillDetailAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("bill_detail").forward(request, response);
    }
}
