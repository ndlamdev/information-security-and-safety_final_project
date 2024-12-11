package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewImage {
    private Integer id, reviewId;
    private String urlImage;
}
