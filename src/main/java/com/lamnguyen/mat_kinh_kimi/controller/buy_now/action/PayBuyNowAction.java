package com.lamnguyen.mat_kinh_kimi.controller.buy_now.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillDTO;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.util.enums.HashAlgorithms;
import com.lamnguyen.mat_kinh_kimi.util.helper.DocumentHelper;
import com.lamnguyen.mat_kinh_kimi.util.mapper.BillMapper;
import com.lamnguyen.mat_kinh_kimi.util.validate.BillValidate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class PayBuyNowAction implements Action {
    private final BillService billService = BillService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Bill bill = BillValidate.validateBill(request, response, "mua_ngay.jsp", true);
        if (bill == null) return;
        var billId = billService.saveBill(bill);
        if (billId != -1) {
            bill.setId(billId);
            var products = List.of((ProductCart) request.getSession().getAttribute("product"));
            BillDTO billDTO = BillMapper.billDTO(bill, products);
            var file = DocumentHelper.createBillFileText(billDTO, request);
            session.setAttribute("products", products);
            session.setAttribute("billPayed", bill);
            session.setAttribute("file", file);
            session.setAttribute("back", "index.jsp");
            request.getServletContext().setAttribute("algorithms", HashAlgorithms.ALGORITHMS);
            response.sendRedirect("xac_nhan_thanh_toan.jsp");
        } else {
            session.setAttribute("title", "Thanh toán không thành công");
            session.setAttribute("message", "1 trong sản phẩm trong danh sách sản phẩm vừa hết hàng.");
            request.getRequestDispatcher("mua_ngay.jsp").forward(request, response);
        }
    }


}