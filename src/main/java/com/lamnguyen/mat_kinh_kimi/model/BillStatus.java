package com.lamnguyen.mat_kinh_kimi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class BillStatus implements Comparator<BillStatus> {
    private Integer billId;
    private String status;
    private String describe;
    private LocalDateTime date;
    private Boolean canEdit;

    public BillStatus() {
    }

    public BillStatus(Integer billId, String status, String describe, Boolean canEdit) {
        this.billId = billId;
        this.status = status;
        this.describe = describe;
        this.date = LocalDateTime.now();
        this.canEdit = canEdit;
    }

    @Override
    public int compare(BillStatus o1, BillStatus o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
