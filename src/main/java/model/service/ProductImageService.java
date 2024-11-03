package model.service;

import model.DAO.ProductImageDAO;
import model.bean.ProductImage;

import java.util.List;

public class ProductImageService {
    private static ProductImageService instance;

    public static ProductImageService getInstance() {
        return instance == null ? new ProductImageService() : instance;
    }

    public List<ProductImage> getProductImage(int productId, int limit) {
        ProductImageDAO productImageDAO = ProductImageDAO.getInstance();
        List<ProductImage> images;
        if (limit == 0) {
            return productImageDAO.getProductImagesNonLimit(productId);
        }

        return productImageDAO.getProductImagesLimit(productId, limit);
    }

    public boolean insert(int productId, List<ProductImage> productImages) {
        boolean result = true;
        for (ProductImage productImage : productImages) {
            productImage.setProductId(productId);
            result &= ProductImageDAO.getInstance().insert(productImage) == 1 ? true : false;
        }

        return result;
    }

    public boolean update(int productId, List<ProductImage> productImages) {
        ProductImageDAO.getInstance().remove(productId);
        return insert(productId, productImages);
    }
}
