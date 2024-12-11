package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class Review {
    private Integer id, productId, billId, userId, numberStar;
    private String comment;
    private List<String> images;
    private User user;
    private LocalDateTime date;
    private List<ReviewImage> reviewImages;

    public void addImage(String image) {
        images = images == null ? new ArrayList<>() : images;
        images.add(image);
    }
}
