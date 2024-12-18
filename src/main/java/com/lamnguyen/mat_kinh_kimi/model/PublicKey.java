package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PublicKey {
    private Integer id, userId;
    private String hashKey;
    private LocalDateTime uploadTime, expired;
    private String verify;
}
