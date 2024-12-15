package com.lamnguyen.mat_kinh_kimi.controller.buy_now.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillDTO;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.util.helper.PDFDocumentHelper;
import com.lamnguyen.mat_kinh_kimi.util.mapper.BillMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class PayBuyNowAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        int codeProvince = 0,
                codeDistrict,
                codeWard;
        User user = (User) session.getAttribute("user");
        BillService billService = (BillService) session.getAttribute("bill-buy-now");
        String userName = request.getParameter("full-name").trim();
        String email = request.getParameter("email").trim();
        String phoneNumber = request.getParameter("phone-number").trim();
        String province = request.getParameter("provinces").trim();
        String district = request.getParameter("districts").trim();
        String ward = request.getParameter("wards").trim();

        String fullAddress = request.getParameter("full-address").trim();
        boolean transfer = request.getParameter("pay-option").equals("transfer");
        String message = null, title = null;

        if (user == null) {
            title = "Bạn chưa đăng nhập";
            message = "Vui lòng đăng nhập trược khi thanh toán. Nhấn \"OK\" để chuyển đến trang đăng nhập";
        }

        if (Double.compare(billService.getTotalBill(), 0) == 0) {
            title = "Bạn chưa chọn sản phẩm";
            message = "Vui lòng chọn sản phẩm bạn muốn mua";
        }

        if (userName.isEmpty()) {
            title = "Tên người nhận rỗng";
            message = "Vui lòng điền tên người nhận";
        }

        if (email.isEmpty()) {
            title = "Email người nhận rỗng";
            message = "Vui lòng điền email người nhận";
        }

        if (phoneNumber.isEmpty()) {
            title = "Số điện thoại người nhận rỗng";
            message = "Vui lòng điền số điện thoại người nhận";
        }

        if (province.isEmpty()) {
            title = "Chưa chọn thành phố/tỉnh người nhận rỗng";
            message = "Vui lòng chọn thành phố/tỉnh người nhận";
        }

        if (district.isEmpty()) {
            title = "Chưa chọn quận/huyện người nhận rỗng";
            message = "Vui lòng chọn quận/huyện người nhận";
        }

        if (ward.isEmpty()) {
            title = "Chưa chọn phường/xã người nhận rỗng";
            message = "Vui lòng chọn phường/xã người nhận";
        }

        if (fullAddress.isEmpty()) {
            title = "Địa chỉ cụ thể người nhận rỗng";
            message = "Vui lòng điền địa chỉ cụ thể người nhận";
        }

        try {
            codeProvince = Integer.parseInt(province);
        } catch (NumberFormatException e) {
            title = "Lỗi chọn thành phố/tỉnh";
            message = "Thành phố/tỉnh người nhận không tồn tại";
        }

        try {
            codeDistrict = Integer.parseInt(district);
        } catch (NumberFormatException e) {
            title = "Lỗi chọn quận/huyện";
            message = "Quận/huyện người nhận không tồn tại";
            return;
        }

        try {
            codeWard = Integer.parseInt(ward);
        } catch (NumberFormatException e) {
            title = "Lỗi chọn phường/xã";
            message = "Phường/xã người nhận không tồn tại";
            return;
        }

        if (message != null) {
            session.setAttribute("title", title);
            session.setAttribute("message", message);
            request.getRequestDispatcher("mua_ngay.jsp").forward(request, response);
            return;
        }

        Bill bill = billService.getBill();
        bill.setUserId(user.getId());
        bill.setUserName(userName);
        bill.setEmail(email);
        bill.setPhoneNumber(phoneNumber);
        bill.setCodeProvince(codeProvince);
        bill.setCodeDistrict(codeDistrict);
        bill.setCodeWard(codeWard);
        bill.setAddress(fullAddress);
        bill.setTransportFee(20000.0);
        bill.setTransfer(transfer);
        bill.setDate(LocalDateTime.now());
        var billId = billService.saveBill(bill);
        if (billId != -1) {
            bill.setId(billId);
            var products = List.of((ProductCart) request.getSession().getAttribute("product"));
            BillDTO billDTO = BillMapper.billDTO(bill, products);
            var file = PDFDocumentHelper.createBillFile(billDTO, request);
            session.setAttribute("products", products);
            session.setAttribute("billPayed", bill);
            session.setAttribute("file", file);
            session.setAttribute("back", "index.jsp");
            response.sendRedirect("xac_nhan_thanh_toan.jsp");
        } else {
            title = "Thanh toán không thành công";
            message = "1 trong sản phẩm trong danh sách sản phẩm vừa hết hàng.";
            session.setAttribute("title", title);
            session.setAttribute("message", message);
            request.getRequestDispatcher("mua_ngay.jsp").forward(request, response);
        }
    }
}