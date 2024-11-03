package model.DAO;

import model.bean.Product;
import model.bean.ProductDiscount;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDiscountDAO extends DAO {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ProductDiscountDAO instance;

    public static ProductDiscountDAO getInstance() {
        return instance == null ? new ProductDiscountDAO() : instance;
    }

    /*
    get Map<productId, pricePercentage>
    @param List<Product> products
    @return Map<Integer, Double>
     */
    public Map<Integer, Double> getPricePercentages(List<Product> products) {
        Map<Integer, Double> result = new LinkedHashMap<Integer, Double>();
        double pricePercentage;
        for (Product product : products) {
            int id = product.getId();
            pricePercentage = getPricePercentage(id);
            result.put(id, pricePercentage);
        }
        return result;
    }

    public Double getPricePercentage(int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT pd.pricePercentage " +
                                "FROM product_discounts AS pd " +
                                "WHERE " +
                                "CURDATE() BETWEEN pd.dateStart AND pd.dateEnd " +
                                "AND pd.productId = ?;")
                        .bind(0, productId)
                        .mapTo(Double.class)
                        .findFirst()
                        .orElse(0.0)
        );
    }

    public int insert(ProductDiscount productDiscount) {
        return connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO product_discounts(productId, pricePercentage, dateStart, dateEnd) VALUES (?, ?, ?, ?);")
                        .bind(0, productDiscount.getProductId())
                        .bind(1, productDiscount.getPricePercentage())
                        .bind(2, productDiscount.getDateStart().format(formatter))
                        .bind(3, productDiscount.getDateEnd().format(formatter))
                        .execute()
        );
    }

    public List<ProductDiscount> getProductDiscounts(int productId) {
        try {
            return connector.withHandle(handle ->
                    handle.createQuery("SELECT pd.pricePercentage, pd.dateStart, pd.dateEnd " +
                                    "FROM product_discounts AS pd " +
                                    "WHERE pd.productId = ?;")
                            .bind(0, productId)
                            .mapToBean(ProductDiscount.class)
                            .list()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<ProductDiscount>();
    }

    public int removeProductId(int productId) {
        return connector.withHandle(handle ->
                handle.createUpdate("DELETE FROM product_discounts WHERE productId = ?")
                        .bind(0, productId)
                        .execute()
        );
    }
}
