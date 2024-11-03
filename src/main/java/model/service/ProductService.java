package model.service;

import model.DAO.ProductDAO;
import model.bean.Product;
import model.bean.ProductCart;
import model.bean.ProductReview;

import java.text.NumberFormat;
import java.util.*;

public class ProductService {
    public static ProductService instance;
    public static final Map<Integer, String> MAP_PAGE = new HashMap<Integer, String>();
    private final String[] REPlAY = {"&page", "&sort-name", "&sort-price"};

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
        ProductService productService = ProductService.getInstance();

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
        ProductDAO productDAO = ProductDAO.getInstance();

        List<Product> products = productDAO.getProduct(id);
        setModel(products);
        setProductImage(products, 0);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);
        setReview(products);

        return products.get(0);
    }


    public Product getProductIdAndName(int productId) {
        ProductDAO productDAO = ProductDAO.getInstance();
        return productDAO.getProductIdAndName(productId);
    }

    public ProductCart getProductCart(int id) {
        ProductDAO productDAO = ProductDAO.getInstance();
        Product product = productDAO.getProductCart(id);
        return new ProductCart(product.getId(), product.getName(), product.getBrandName(), product.getDescribe(), product.getCategoryName(), product.getPrice(), 0.0, null, 0);
    }

    public ProductCart getProductBill(int id) {
        ProductDAO productDAO = ProductDAO.getInstance();
        Product product = productDAO.getProductBill(id);
        return new ProductCart(product.getId(), product.getName(), product.getBrandName(), product.getDescribe(), product.getCategoryName(), product.getPrice(), 0.0, null, 0);
    }

    /**
     * Lấy các sản phẩm theo câu query
     */
    public List<Product> getProducts(Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort, int limit) {
        ProductDAO productDAO = ProductDAO.getInstance();

        List<Product> products = productDAO.getProducts(mapInfRoot, mapFilter, mapSort, limit);
        setModel(products);
        setProductImage(products, 2);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);

        return products;
    }

    public Product getRecentlyViewedProduct(int productId) {
        ProductDAO productDAO = ProductDAO.getInstance();

        Product product = productDAO.getRecentlyViewedProduct(productId);
        List<Product> products = new ArrayList<>();
        products.add(product);
        setModel(products);
        setProductImage(products, 2);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);

        return products.get(0);
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
        ProductDAO productDAO = ProductDAO.getInstance();

        return productDAO.totalPages(mapInfRoot, mapFilter, mapSort);
    }

    /*
     * Xữ lý lại địa chỉ thanh URL cho hợp lý
     * */
    public String formatQueryRequest(String query) {
        StringTokenizer tk = new StringTokenizer(query, "&=");
        String oldRequest = query.substring(0, query.lastIndexOf("&"));
        String newRequest = query.substring(query.lastIndexOf("&"), query.length());
        String name;
        StringBuilder sb = new StringBuilder();


        if (oldRequest.contains(newRequest)) {
            return oldRequest.replace(newRequest, "");
        }

        for (String key : REPlAY) {
            if (oldRequest.contains(key) && newRequest.startsWith(key)) {
                int indexFirstFound = oldRequest.indexOf(key), indexBright = query.indexOf("&", indexFirstFound + 1), length = oldRequest.length();
                if (key.startsWith("&page"))
                    return oldRequest.substring(0, indexFirstFound)
                            + newRequest
                            + oldRequest.substring(indexBright, length);
                return oldRequest.substring(0, indexFirstFound)
                        + newRequest;
            }
        }

        if (newRequest.contains("sort-none") || newRequest.contains("filter-none")) {
            while (tk.hasMoreTokens()) {
                name = tk.nextToken();

                if (name.startsWith("sort") || name.startsWith("filter")) {
                    tk.nextToken();
                } else {
                    if (!sb.isEmpty()) sb.append("&");
                    sb.append(name);
                    sb.append("=");
                    sb.append(tk.nextToken());
                }
            }

            return sb.toString();
        }

        return query;
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
        ModelService modelService = new ModelService();
        int id;
        for (Product product : products) {
            id = product.getId();
            product.setModels(modelService.getModelsByProductId(id));
        }
    }

    private void setProductImage(List<Product> products, int limit) {
        ProductImageService productImageService = new ProductImageService();
        int id;
        for (Product product : products) {
            id = product.getId();
            product.setProductImages(productImageService.getProductImage(id, limit));
        }
    }

    private void setStarNumber(List<Product> products) {
        ReviewService reviewService = new ReviewService();
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
        ProductDiscountService productDiscountService = ProductDiscountService.getInstance();
        Map<Integer, Double> mapProductPricePercentage = productDiscountService.getPricePercentages(products);
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
        int id = 0;
        for (Product product : products) {
            id = product.getId();
            product.setTotalQuantitySold(billDetailService.getTotalQuantitySold(id));
        }
    }

    public List<Product> getProductDiscount(int limit) {
        Map<String, Integer> mapinfoRoot = new HashMap<String, Integer>();
        mapinfoRoot.put("page", 1);
        mapinfoRoot.put("id-category-group", 0);
        mapinfoRoot.put("id-category", 0);
        return getProducts(mapinfoRoot, new HashMap<>(), new HashMap<>(), limit);
    }

    public List<Product> getInfoProminentProductByStart(int limit) {
        List<Product> products = ProductDAO.getInstance().getInfoProminentProductByStart(limit);
        setModel(products);
        setProductImage(products, 2);
        setStarNumber(products);
        setReducedPrice(products);
        setTotalQuantitySold(products);
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return -o1.getStarNumber().compareTo(o2.getStarNumber());
            }
        });
        return products;
    }

    public List<String> getBrands() {
        return ProductDAO.getInstance().getBrands();
    }

    public ProductReview getProductReview(int userId, int productId, int modelId) {
        ProductDAO productDAO = ProductDAO.getInstance();
        return productDAO.getProductReview(userId, productId, modelId);
    }

    public List<ProductReview> getProductReviewsHaveEvaluated(int userId, int offset) {
        ProductDAO productDAO = ProductDAO.getInstance();

        return productDAO.getProductReviewsHaveEvaluated(userId, offset);
    }

    public List<ProductReview> getProductReviewsNotYetRated(int userId, int offset) {
        ProductDAO productDAO = ProductDAO.getInstance();
        return productDAO.getProductReviewsNotYetRated(userId, offset);
    }

    public List<String> getMaterialsForAdmin() {
        return ProductDAO.getInstance().getMaterialsForAdmin();
    }

    public List<String> getTypesForAdmin() {
        return ProductDAO.getInstance().getTypesForAdmin();
    }

    public List<String> getMaterials() {
        return ProductDAO.getInstance().getMaterials();
    }

    public List<String> getTypes() {
        return ProductDAO.getInstance().getTypes();
    }

    public int createProductTemp() {
        return ProductDAO.getInstance().createProductTemp();
    }

    public int insert(Product product) {
        int result = ProductDAO.getInstance().update(product);
        if (result != 0) {
            ModelService modelService = ModelService.getInstance();
            modelService.insert(product.getId(), product.getModels());
            ProductImageService productImageService = ProductImageService.getInstance();
            productImageService.insert(product.getId(), product.getProductImages());
            ProductDiscountService productDiscount = ProductDiscountService.getInstance();
            productDiscount.insert(product.getId(), product.getProductDiscounts());
        }
        return result;
    }

    public boolean delete(int productId) {
        return ProductDAO.getInstance().delete(productId);
    }

    public boolean lock(int productId) {
        return ProductDAO.getInstance().lock(productId);
    }

    public List<Product> getProductForAdmin(String name, int categoryGroupId, int categoryId, String brandName, int available, int limit, int offset) {
        ProductDAO productDAO = ProductDAO.getInstance();

        List<Product> products = productDAO.getProductForAdmin(name, categoryGroupId, categoryId, "%" + brandName + "%", available, limit, offset);
        setModel(products);

        return products;
    }


    public int totalProduct(String name, int categoryGroupId, int categoryId, String brandName, int available) {
        return ProductDAO.getInstance().totalProduct(name, categoryGroupId, categoryId, "%" + brandName + "%", available);
    }

    public Product getProductEdit(int id) {
        ProductDAO productDAO = ProductDAO.getInstance();
        List<Product> products = productDAO.getProductEdit(id);
        setModel(products);
        setProductImage(products, 0);
        setProductDiscounts(products);
        return products.get(0);
    }

    private void setProductDiscounts(List<Product> products) {
        ProductDiscountService productDiscountService = ProductDiscountService.getInstance();
        for (Product product : products) {
            product.parseProductDiscounts(productDiscountService.getProductDiscounts(product.getId()));
        }
    }

    public int update(Product product) {
        int result = ProductDAO.getInstance().update(product);
        if (result != 0) {
            ModelService modelService = ModelService.getInstance();
            modelService.update(product.getId(), product.getModels());
            ProductImageService productImageService = ProductImageService.getInstance();
            productImageService.update(product.getId(), product.getProductImages());
            ProductDiscountService productDiscount = ProductDiscountService.getInstance();
            productDiscount.update(product.getId(), product.getProductDiscounts());
        }
        return result;
    }

    public void unlock(int productId) {
        ProductDAO.getInstance().unlock(productId);
    }

    public List<Product> getSearchProducts(String name) {
        ProductDAO productDAO = ProductDAO.getInstance();
        List<Product> products = productDAO.getSearchProducts(name);
        setProductImage(products, 1);
        return products;
    }

    public List<Product> getProductsIdAndName(){
        return ProductDAO.getInstance().getProductsIdAndName();
    }
}
