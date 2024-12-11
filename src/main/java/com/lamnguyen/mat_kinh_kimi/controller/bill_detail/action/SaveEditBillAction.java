package com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.service.AddressService;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveEditBillAction implements Action {
    private final AddressService ADDRESS_SERVICE;

    public SaveEditBillAction() {
        ADDRESS_SERVICE = AddressService.getInstance();
    }

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdString = request.getParameter("bill-id"),
                name = request.getParameter("name"),
                phoneNumber = request.getParameter("phone-number"),
                email = request.getParameter("email"),
                address = request.getParameter("address"),
                provinceCodeString = request.getParameter("province-code"),
                districtCodeString = request.getParameter("district-code"),
                wardCodeString = request.getParameter("ward-code");
        int billId;
        int provinceCode;
        int districtCode;
        int wardCode;
        try {
            billId = Integer.parseInt(billIdString);
            provinceCode = Integer.parseInt(provinceCodeString);
            districtCode = Integer.parseInt(districtCodeString);
            wardCode = Integer.parseInt(wardCodeString);
        } catch (NumberFormatException e) {
            response.setStatus(404);
            response.getWriter().println(new JSONObject());
            return;
        }

        BillService billService = BillService.getInstance();
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setUserName(name);
        bill.setPhoneNumber(phoneNumber);
        bill.setEmail(email);
        bill.setAddress(address);
        bill.setCodeProvince(provinceCode);
        bill.setCodeDistrict(districtCode);
        bill.setCodeWard(wardCode);
        billService.updateContact(bill);

        String addressDetails = ADDRESS_SERVICE.getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                "<br>" + bill.getAddress();
        JSONObject json = new JSONObject();
        json.put("addressDetail", addressDetails);
        response.getWriter().println(json);
    }
}
