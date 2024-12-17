package com.lamnguyen.mat_kinh_kimi.controller.bill;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.util.helper.DocumentHelper;
import com.lamnguyen.mat_kinh_kimi.util.mapper.BillMapper;
import com.lamnguyen.mat_kinh_kimi.util.validate.BillValidate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BillController", value = "/bill")
public class BillController extends HttpServlet implements Action {
    private final BillService billService = BillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        var session = request.getSession();

        Bill bill = BillValidate.validateBill(request, response, "gio_hang.jsp", false);
        if (bill == null) return;
        var billId = billService.saveBill(bill);
        if (billId != -1) {
            bill.setId(billId);
            var products = billService.getProductInBill(billId);
            var file = DocumentHelper.createBillFileText(BillMapper.billDTO(bill, billService.getProductInBill(billId)), request);
            session.setAttribute("products", products);
            session.setAttribute("billPayed", bill);
            session.setAttribute("file", file);
            session.setAttribute("back", "gio_hang.jsp");
            response.sendRedirect("xac_nhan_thanh_toan.jsp");
        } else {
            session.setAttribute("title", "Thanh toán không thành công");
            session.setAttribute("message", "1 trong sản phẩm trong danh sách sản phẩm vừa hết hàng.");
            response.sendRedirect("gio_hang.jsp");
        }
    }
}