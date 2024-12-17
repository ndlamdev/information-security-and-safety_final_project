package com.lamnguyen.mat_kinh_kimi.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class BillWillDelete {
    private Integer id;
    private String status;
    private LocalDateTime date;
}
