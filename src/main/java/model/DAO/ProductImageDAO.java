package model.DAO;

import model.bean.Product;
import model.bean.ProductImage;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductImageDAO extends DAO {
    private static ProductImageDAO instance;

    public static ProductImageDAO getInstance() {
        return instance == null ? new ProductImageDAO() : instance;
    }

    public List<ProductImage> getProductImagesNonLimit(int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT pimg.id, pimg.urlImage " +
                                "FROM product_images AS pimg " +
                                "WHERE " +
                                "pimg.productId = ?;")
                        .bind(0, productId)
                        .mapToBean(ProductImage.class)
                        .list()
        );
    }

    public List<ProductImage> getProductImagesLimit(int productId, int limit) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT pimg.id, pimg.urlImage " +
                                "FROM product_images AS pimg " +
                                "WHERE " +
                                "pimg.productId = ? " +
                                "LIMIT ?;")
                        .bind(0, productId)
                        .bind(1, limit)
                        .mapToBean(ProductImage.class)
                        .list()
        );
    }

    public int insert(ProductImage productImage) {
        String sql = "INSERT INTO product_images(productId, urlImage) values(?, ?);";
        return connector.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, productImage.getProductId())
                        .bind(1, productImage.getUrlImage())
                        .execute()
        );
    }

    public int remove(int productId) {
        return connector.withHandle(handle ->
                handle.createUpdate("DELETE FROM product_images WHERE productid = ?")
                        .bind(0, productId)
                        .execute()
        );
    }
}
