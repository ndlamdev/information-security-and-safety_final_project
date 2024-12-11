package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class CategoryGroup {
    private Integer id;
    private String name;
    private List<Category> categories;
}
