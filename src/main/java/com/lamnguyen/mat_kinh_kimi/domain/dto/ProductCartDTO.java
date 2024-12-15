package com.lamnguyen.mat_kinh_kimi.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductCartDTO {
    private String name, model;
    private double price;
    private int quantity;
}
