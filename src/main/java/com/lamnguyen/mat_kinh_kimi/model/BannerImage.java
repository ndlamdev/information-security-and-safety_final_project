package com.lamnguyen.mat_kinh_kimi.model;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class BannerImage {
    private Integer id;
    private String description, urlImage;

    public BannerImage() {
    }

    public BannerImage(Integer id, String description, String urlImage) {
        this.id = id;
        this.description = description;
        this.urlImage = urlImage;
    }
}
