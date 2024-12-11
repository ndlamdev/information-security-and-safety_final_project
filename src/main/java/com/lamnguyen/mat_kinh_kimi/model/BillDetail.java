package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BillDetail {
    private Integer billId, productId, modelId, quantity;
    private Double price;

    public BillDetail() {
    }

    public BillDetail(Integer productId, Integer modelId, Integer quantity, Double price) {
        this.productId = productId;
        this.modelId = modelId;
        this.quantity = quantity;
        this.price = price;
    }
}
