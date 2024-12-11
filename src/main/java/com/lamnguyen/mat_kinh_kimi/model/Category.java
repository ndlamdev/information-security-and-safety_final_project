package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Category {
    private Integer id, categoryGroupId;
    private String name;
    private CategoryGroup categoryGroup;
}
