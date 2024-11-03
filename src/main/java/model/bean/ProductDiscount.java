package model.bean;


import java.time.LocalDateTime;
import java.util.StringTokenizer;

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

    public ProductDiscount() {
    }

    public Integer getProductId() {
        return productId;
    }

    public Double getPricePercentage() {
        return pricePercentage;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setPricePercentage(Double pricePercentage) {
        this.pricePercentage = pricePercentage;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void parseDateStart(String dataStr) {
        StringTokenizer tk = new StringTokenizer(dataStr, "-");
        this.dateStart = LocalDateTime.of(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), 0, 0, 0);
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void parseDateEnd(String dataStr) {
        StringTokenizer tk = new StringTokenizer(dataStr, "-");
        this.dateEnd = LocalDateTime.of(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), 0, 0, 0);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "ProductDiscount{" +
                "productId=" + productId +
                ", pricePercentage=" + pricePercentage +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}

