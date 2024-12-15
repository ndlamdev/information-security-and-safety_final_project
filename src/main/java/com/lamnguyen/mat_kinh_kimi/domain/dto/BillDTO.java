package com.lamnguyen.mat_kinh_kimi.domain.dto;

import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class BillDTO implements Serializable {
    int id;
    private String name, email, phone, address, payment, note;
    private LocalDateTime date;
    @Builder.Default
    private List<ProductCart> products = new ArrayList<>();
}
