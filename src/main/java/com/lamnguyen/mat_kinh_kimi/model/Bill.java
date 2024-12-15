package com.lamnguyen.mat_kinh_kimi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Bill {
    private Integer id, userId, codeProvince, codeDistrict, codeWard;
    private String userName, phoneNumber, address, email, signature;
    private Boolean transfer, verify;
    private Double transportFee;
    private List<BillStatus> statuses;
    private List<BillDetail> details;
    private LocalDateTime dateTimeSign;

    private String addressDetail;

    public Bill() {
    }

    public Boolean isTransfer() {
        return transfer;
    }

    public void addStatus(BillStatus status) {
        if (statuses == null) {
            statuses = new ArrayList<>();
        }

        statuses.add(status);
    }

    public void addDetail(BillDetail detail) {
        if (details == null) {
            details = new ArrayList<>();
        }

        details.add(detail);
    }

    public double totalBill() {
        double total = 0;
        for (BillDetail bd : this.getDetails()) {
            total += bd.getPrice() * bd.getQuantity();
        }
        return total + transportFee;
    }

    public BillStatus getLastStatus() {
        return this.statuses.getLast();
    }

    public boolean canEdit() {
        return getLastStatus().getCanEdit();
    }
}
