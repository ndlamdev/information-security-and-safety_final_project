package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductReview {
    private Integer productId, modelId, quantity;
    private double price;
    private String productName, modelName, urlImage;
}
