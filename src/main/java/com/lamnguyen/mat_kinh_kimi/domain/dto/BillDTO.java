package com.lamnguyen.mat_kinh_kimi.domain.dto;

import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BillDTO  implements Serializable {
    private String name, email, phone, address, note;
    private LocalDateTime date;
    private List<ProductCart> products;
}
