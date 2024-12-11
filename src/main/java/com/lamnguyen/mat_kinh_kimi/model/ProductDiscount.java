package com.lamnguyen.mat_kinh_kimi.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.StringTokenizer;

@Setter
@Getter
@ToString
public class ProductDiscount {
    private Integer productId, id;
    private Double pricePercentage;

    private LocalDateTime dateStart, dateEnd;

    public ProductDiscount(String productDiscountStr) {
        StringTokenizer tk = new StringTokenizer(productDiscountStr, ",");
        try {
            this.pricePercentage = Double.parseDouble(tk.nextToken());
            this.parseDateStart(tk.nextToken());
            this.parseDateEnd(tk.nextToken());
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void parseDateStart(String dataStr) {
        StringTokenizer tk = new StringTokenizer(dataStr, "-");
        this.dateStart = LocalDateTime.of(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), 0, 0, 0);
    }

    public void parseDateEnd(String dataStr) {
        StringTokenizer tk = new StringTokenizer(dataStr, "-");
        this.dateEnd = LocalDateTime.of(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), 0, 0, 0);
    }
}

