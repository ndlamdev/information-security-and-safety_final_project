package com.lamnguyen.mat_kinh_kimi.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductImage {
    private Integer id, productId;
    private String urlImage;

    public ProductImage(String data) {
        this.urlImage = data;
    }

    public ProductImage() {
    }
}
