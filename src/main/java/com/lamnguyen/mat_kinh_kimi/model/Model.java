package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.StringTokenizer;

@Setter
@Getter
@ToString
public class Model {
    private Integer id, productId, quantity, totalQuantitySold;
    private String name, urlImage;

    public Model(String data) {
        StringTokenizer tk = new StringTokenizer(data, ",");
        this.name = tk.nextToken();
        this.quantity = Integer.parseInt(tk.nextToken());
        this.urlImage = tk.nextToken();
    }

    public Model() {
    }

    public Integer getTotalQuantitySold() {
        return totalQuantitySold == null ? 0 : totalQuantitySold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, urlImage);
    }

    public boolean available() {
        return this.quantity > this.getTotalQuantitySold();
    }
}
