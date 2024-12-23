package com.lamnguyen.mat_kinh_kimi.controller.download_bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DownloadBill", value = "/download-bill")
public class DownloadBillController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        // Set response content type
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=order_form.pdf");
        HttpSession session = req.getSession();

    }
}