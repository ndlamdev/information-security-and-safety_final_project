package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.ProductRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import com.lamnguyen.mat_kinh_kimi.model.ProductReview;

import java.text.NumberFormat;
import java.util.*;

public class ProductService {
    public static ProductService instance;
    public static final Map<Integer, String> MAP_PAGE = new HashMap<>();
    private final String[] REPlAY = {"&page", "&sort-name", "&sort-price"};
    private final ModelService MODEL_SERVICE;
    private final ProductImageService PRODUCT_IMAGE_SERVICE;
    private final ProductDiscountService PRODUCT_DISCOUNT_SERVICE;
    private final ProductRepositoryImpl PRODUCT_REPOSITORY;

    static {
        MAP_PAGE.put(0, "Khuyến mãi");
        MAP_PAGE.put(1, "Kính mát nam");
        MAP_PAGE.put(2, "Kính mát nữ");
        MAP_PAGE.put(3, "Kính đi ngày và đêm");
        MAP_PAGE.put(4, "Kính đổi màu");
        MAP_PAGE.put(5, "Kính lọc ánh sáng xanh");
        MAP_PAGE.put(6, "Kính mắt clip on 2 lớp");
        MAP_PAGE.put(7, "Gọng kính trẻ en");
        MAP_PAGE.put(8, "Kính mát trẻ em");
        MAP_PAGE.put(9, "Gọng kính nữa khung");
        MAP_PAGE.put(10, "Gọng kính khoan");
        MAP_PAGE.put(11, "Gọng kính tròn");
        MAP_PAGE.put(12, "Gọng kính titan");
        MAP_PAGE.put(13, "Tròng kính chống ánh sáng xanh");
        MAP_PAGE.put(14, "Tròng kính đổi màu");
        MAP_PAGE.put(15, "Tròng kính màu");
        MAP_PAGE.put(16, "Tròng kính cho gọng khoan");

        MAP_PAGE.put(17, "Kính mát");
        MAP_PAGE.put(18, "Mắt kính trẻ em");
        MAP_PAGE.put(19, "Gọng kính");
        MAP_PAGE.put(20, "Tròng kính");
    }


    private ProductService() {
        PRODUCT_IMAGE_SERVICE = ProductImageService.getInstance();
        MODEL_SERVICE = ModelService.getInstance();
        PRODUCT_DISCOUNT_SERVICE = ProductDiscountService.getInstance();
        PRODUCT_REPOSITORY = ProductRepositoryImpl.getInstance();
    }

    public static ProductService getInstance() {
        return instance == null ? new ProductService() : instance;
    }

    /**
     * Lấy tiêu đề trang
     *
     * @param idCategoryGroup id nhóm danh mục
     * @param idCategory      id danh mục
     */
    public String getTitle(int idCategoryGroup, int idCategory) {
        if (idCategoryGroup == 0 && idCategory == 0) {
            return MAP_PAGE.get(idCategory);
        }
        if (idCategoryGroup != 0 && idCategory == 0) {
            return MAP_PAGE.get(16 + idCategoryGroup);
        }
        if (idCategoryGroup != 0 && idCategory != 0) {
            return MAP_PAGE.get(idCategory);
        }
        return null;
    }

    public Product getProduct(int id) {
        List<Product> products = PRODUCT_REPOSITORY.getProduct(id);
        setModel(products);
        setProductImage(products, 0);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);
        setReview(products);

        return products.getFirst();
    }


    public Product getProductIdAndName(int productId) {
        return PRODUCT_REPOSITORY.getProductIdAndName(productId);
    }

    public ProductCart getProductCart(int id) {
        Product product = PRODUCT_REPOSITORY.getProductCart(id);
        return new ProductCart(product.getId(), product.getName(), product.getBrandName(), product.getDescribe(), product.getCategoryName(), product.getPrice(), 0.0, null, 0);
    }

    public ProductCart getProductBill(int id) {
        Product product = PRODUCT_REPOSITORY.getProductBill(id);
        return new ProductCart(product.getId(), product.getName(), product.getBrandName(), product.getDescribe(), product.getCategoryName(), product.getPrice(), 0.0, null, 0);
    }

    /**
     * Lấy các sản phẩm theo câu query
     */
    public List<Product> getProducts(Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort, int limit) {
        List<Product> products = PRODUCT_REPOSITORY.getProducts(mapInfRoot, mapFilter, mapSort, limit);
        setModel(products);
        setProductImage(products, 2);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);

        return products;
    }

    public Product getRecentlyViewedProduct(int productId) {
        Product product = PRODUCT_REPOSITORY.getRecentlyViewedProduct(productId);
        List<Product> products = new ArrayList<>();
        products.add(product);
        setModel(products);
        setProductImage(products, 2);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);

        return products.getFirst();
    }

    public Map<String, List<String>> getMapFilter(String query) {
        Map<String, List<String>> mapFilter = new LinkedHashMap<>();
        List<String> values;
        StringTokenizer tk;
        String name;
        String valueStr;

        tk = new StringTokenizer(query, "&=");
        while (tk.hasMoreTokens()) {
            name = tk.nextToken();
            if (name.startsWith("filter")) {
                valueStr = tk.nextToken();
                values = mapFilter.get(name);
                values = values == null ? new ArrayList<>() : values;
                values.add(valueStr);
                mapFilter.put(name, values);
            }
        }

        return mapFilter;
    }

    public Map<String, String> getMapSort(String query) {
        Map<String, String> mapSort = new LinkedHashMap<>();
        StringTokenizer tk;
        String name;
        String valueStr;

        tk = new StringTokenizer(query, "&=");
        while (tk.hasMoreTokens()) {
            name = tk.nextToken();
            if (name.startsWith("sort")) {
                valueStr = tk.nextToken();
                if (!query.contains("sort-none")) mapSort.put(name, valueStr);
            }
        }

        return mapSort;
    }

    public Map<String, Integer> getMapInfRoot(String query) {
        Map<String, Integer> mapInfRoot = new LinkedHashMap<>();
        StringTokenizer tk;
        String name;
        int valueInt;

        tk = new StringTokenizer(query, "&=");
        while (tk.hasMoreTokens()) {
            name = tk.nextToken();
            if (name.startsWith("id") || name.startsWith("page")) {
                valueInt = Integer.parseInt(tk.nextToken());
                mapInfRoot.put(name, valueInt);
            }
        }


        return mapInfRoot;
    }

    public int getTotalPages(Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort) {
        return PRODUCT_REPOSITORY.totalPages(mapInfRoot, mapFilter, mapSort);
    }

    private void setReview(List<Product> products) {
        ReviewService reviewService = ReviewService.getInstance();
        int id;
        for (Product product : products) {
            id = product.getId();
            product.setReviews(reviewService.getReviews(id));
        }
    }

    private void setModel(List<Product> products) {
        int id;
        for (Product product : products) {
            id = product.getId();
            product.setModels(MODEL_SERVICE.getModelsByProductId(id));
        }
    }

    private void setProductImage(List<Product> products, int limit) {

        int id;
        for (Product product : products) {
            id = product.getId();
            product.setProductImages(PRODUCT_IMAGE_SERVICE.getProductImage(id, limit));
        }
    }

    private void setStarNumber(List<Product> products) {
        ReviewService reviewService = ReviewService.getInstance();
        Map<Integer, InfReview> mapStarNumber = reviewService.getInfReview(products);
        int id;
        for (Product product : products) {
            id = product.getId();
            InfReview infReview = mapStarNumber.get(id);
            product.setStarNumber(infReview.getStarNumber());
            product.setTotalReview(infReview.getTotalReview());
        }
    }

    private void setReducedPrice(List<Product> products) {
        Map<Integer, Double> mapProductPricePercentage = PRODUCT_DISCOUNT_SERVICE.getPricePercentages(products);
        int id;
        double pricePercentage, discount;

        NumberFormat nf = NumberFormat.getCompactNumberInstance();
        nf.setMaximumFractionDigits(2);
        for (Product product : products) {
            id = product.getId();
            pricePercentage = mapProductPricePercentage.get(id);
            discount = product.getPrice() * (1.0 - pricePercentage);
            if (Double.compare(pricePercentage, 0) == 0) {
                discount = 0;
            }

            product.setDiscount(discount);
        }
    }

    private void setTotalQuantitySold(List<Product> products) {
        BillDetailService billDetailService = BillDetailService.getInstance();
        int id;
        for (Product product : products) {
            id = product.getId();
            product.setTotalQuantitySold(billDetailService.getTotalQuantitySold(id));
        }
    }

    public List<Product> getProductDiscount(int limit) {
        Map<String, Integer> mapinfoRoot = new HashMap<>();
        mapinfoRoot.put("page", 1);
        mapinfoRoot.put("id-category-group", 0);
        mapinfoRoot.put("id-category", 0);
        return getProducts(mapinfoRoot, new HashMap<>(), new HashMap<>(), limit);
    }

    public List<Product> getInfoProminentProductByStart(int limit) {
        List<Product> products = PRODUCT_REPOSITORY.getInfoProminentProductByStart(limit);
        setModel(products);
        setProductImage(products, 2);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);
        products.sort((o1, o2) -> -o1.getStarNumber().compareTo(o2.getStarNumber()));
        return products;
    }

    public List<String> getBrands() {
        return PRODUCT_REPOSITORY.getBrands();
    }

    public ProductReview getProductReview(int userId, int productId, int modelId) {
        return PRODUCT_REPOSITORY.getProductReview(userId, productId, modelId);
    }

    public List<ProductReview> getProductReviewsHaveEvaluated(int userId, int offset) {
        return PRODUCT_REPOSITORY.getProductReviewsHaveEvaluated(userId, offset);
    }

    public List<ProductReview> getProductReviewsNotYetRated(int userId, int offset) {
        return PRODUCT_REPOSITORY.getProductReviewsNotYetRated(userId, offset);
    }

    public List<String> getMaterialsForAdmin() {
        return PRODUCT_REPOSITORY.getMaterialsForAdmin();
    }

    public List<String> getTypesForAdmin() {
        return PRODUCT_REPOSITORY.getTypesForAdmin();
    }

    public List<String> getMaterials() {
        return PRODUCT_REPOSITORY.getMaterials();
    }

    public List<String> getTypes() {
        return PRODUCT_REPOSITORY.getTypes();
    }

    public int createProductTemp() {
        return PRODUCT_REPOSITORY.createProductTemp();
    }

    public int insert(Product product) {
        int result = PRODUCT_REPOSITORY.update(product);
        if (result != 0) {
            MODEL_SERVICE.insert(product.getId(), product.getModels());
            PRODUCT_IMAGE_SERVICE.insert(product.getId(), product.getProductImages());
            PRODUCT_DISCOUNT_SERVICE.insert(product.getId(), product.getProductDiscounts());
        }
        return result;
    }

    public boolean delete(int productId) {
        return PRODUCT_REPOSITORY.delete(productId);
    }

    public boolean lock(int productId) {
        return PRODUCT_REPOSITORY.lock(productId);
    }

    public List<Product> getProductForAdmin(String name, int categoryGroupId, int categoryId, String brandName, int available, int limit, int offset) {
        List<Product> products = PRODUCT_REPOSITORY.getProductForAdmin(name, categoryGroupId, categoryId, "%" + brandName + "%", available, limit, offset);
        setModel(products);

        return products;
    }


    public int totalProduct(String name, int categoryGroupId, int categoryId, String brandName, int available) {
        return PRODUCT_REPOSITORY.totalProduct(name, categoryGroupId, categoryId, "%" + brandName + "%", available);
    }

    public Product getProductEdit(int id) {
        List<Product> products = PRODUCT_REPOSITORY.getProductEdit(id);
        setModel(products);
        setProductImage(products, 0);
        setProductDiscounts(products);
        return products.getFirst();
    }

    private void setProductDiscounts(List<Product> products) {
        for (Product product : products) {
            product.setProductDiscounts(PRODUCT_DISCOUNT_SERVICE.getProductDiscounts(product.getId()));
        }
    }

    public int update(Product product) {
        int result = PRODUCT_REPOSITORY.update(product);
        if (result != 0) {
            MODEL_SERVICE.update(product.getId(), product.getModels());
            PRODUCT_IMAGE_SERVICE.update(product.getId(), product.getProductImages());
            PRODUCT_DISCOUNT_SERVICE.update(product.getId(), product.getProductDiscounts());
        }
        return result;
    }

    public void unlock(int productId) {
        PRODUCT_REPOSITORY.unlock(productId);
    }

    public List<Product> getSearchProducts(String name) {
        List<Product> products = PRODUCT_REPOSITORY.getSearchProducts(name);
        setProductImage(products, 1);
        return products;
    }

    public List<Product> getProductsIdAndName() {
        return PRODUCT_REPOSITORY.getProductsIdAndName();
    }
}
