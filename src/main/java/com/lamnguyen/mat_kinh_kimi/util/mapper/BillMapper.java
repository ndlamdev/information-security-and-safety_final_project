/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:06 AM - 15/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.util.mapper;

import com.lamnguyen.mat_kinh_kimi.domain.dto.BillDTO;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.ProductCart;

import java.util.List;

public class BillMapper {
    public static BillDTO billDTO(Bill bill, List<ProductCart> products) {
        return BillDTO.builder()
                .id(bill.getId())
                .name(bill.getUserName())
                .email(bill.getEmail())
                .phone(bill.getPhoneNumber())
                .address(bill.getAddress())
                .payment(bill.getTransfer() ? "Chuyển khoản" : "Tiền mặt")
                .date(bill.getDateTimeSign())
                .note("")
                .products(products)
                .build();
    }
}
