package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.model.ProductReview;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.*;

public class ProductRepositoryImpl extends Repository {
    private final String WHERE_PRODUCT_DONT_DELETE = " WHERE p.`delete` = 0 ";
    private final String WHERE_NOT_JOIN = " WHERE p.categoryId = ? ";
    private final String WHERE_JOIN_1 = " WHERE CURRENT_TIMESTAMP() BETWEEN pd.dateStart AND pd.dateEnd ";
    private final String WHERE_JOIN_2 = " WHERE c.categoryGroupId = ? ";
    private final String WHERE_JOIN_3 = " WHERE p.id = ? ";
    private final String JOIN_1 = " JOIN product_discounts AS pd ON pd.productId = p.id ";
    private final String JOIN_2 = " JOIN categories AS c ON c.id = p.categoryId ";
    private final String LIMIT_OFFSET = " LIMIT ? OFFSET ? ";
    private final Map<String, String> MAP_SQL_SORT = new HashMap<>();
    private final Map<String, String> MAP_SQL_FILTER = new HashMap<>();
    private final int LIMIT = 20;
    private static ProductRepositoryImpl instance;

    private ProductRepositoryImpl() {
        initMapSQL();
    }

    public static ProductRepositoryImpl getInstance() {
        return (instance = instance == null ? new ProductRepositoryImpl() : instance);
    }

    private void initMapSQL() {
        if (MAP_SQL_SORT.isEmpty()) {
            MAP_SQL_SORT.put("sort-price", " p.price = ? ");
            MAP_SQL_SORT.put("sort-name", " p.name = ? ");
        }

        if (MAP_SQL_FILTER.isEmpty()) {
            MAP_SQL_FILTER.put("com.lamnguyen.mat_kinh_kimi.filter-brand", " AND brandName IN ");
            MAP_SQL_FILTER.put("com.lamnguyen.mat_kinh_kimi.filter-material", " AND material IN ");
            MAP_SQL_FILTER.put("com.lamnguyen.mat_kinh_kimi.filter-type", " AND type IN ");
            MAP_SQL_FILTER.put("com.lamnguyen.mat_kinh_kimi.filter-price", " price BETWEEN ? AND ? OR ");
        }
    }

    public List<Product> getProduct(int id) {
        List<Product> result;
        int index = 0;
        String select = " p.id, c.name as categoryName, p.name, p.brandName, p.price, p.describe, p.`type`, p.`material` ";

        String sql = initSQLGetProduct(select);
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, id)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> getProductEdit(int id) {
        List<Product> result;
        String select = " p.id, c.name as categoryName, p.name, p.brandName, p.TYPE, p.material, p.price, p.describe ";

        StringBuilder sql = new StringBuilder("SELECT " + select + " FROM products AS p ");
        sql.append(JOIN_2);
        sql.append(WHERE_JOIN_3);

        int lastIndexOfWhere = sql.lastIndexOf("WHERE");
        if (sql.indexOf("WHERE") != lastIndexOfWhere) sql.replace(lastIndexOfWhere, lastIndexOfWhere + 5, "AND ");

        return connector.withHandle(handle ->
                handle.createQuery(sql.toString())
                        .bind(0, id)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public Product getProductCart(int id) {
        int index = 0;
        String select = " p.id, c.name as categoryName, p.name, p.brandName, p.price ";

        String sql = initSQLGetProduct(select);
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, id)
                        .mapToBean(Product.class)
                        .findFirst().orElse(null)
        );
    }

    public Product getProductBill(int id) {
        int index = 0;
        String select = " p.id, c.name as categoryName, p.name, p.brandName, p.price ";

        StringBuilder sql = new StringBuilder("SELECT " + select + " FROM products AS p ");
        sql.append(JOIN_2);
        sql.append(WHERE_JOIN_3);

        int lastIndexOfWhere = sql.lastIndexOf("WHERE");
        if (sql.indexOf("WHERE") != lastIndexOfWhere) sql.replace(lastIndexOfWhere, lastIndexOfWhere + 5, "AND ");

        return connector.withHandle(handle ->
                handle.createQuery(sql.toString())
                        .bind(0, id)
                        .mapToBean(Product.class)
                        .findFirst().orElse(null)
        );
    }

    public Product getProductIdAndName(int id) {
        int index = 0;
        String select = " p.id, p.name ";

        String sql = initSQLGetProduct(select);
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, id)
                        .mapToBean(Product.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    private String initSQLGetProduct(String select) {
        StringBuilder sql = new StringBuilder("SELECT " + select + " FROM products AS p ");
        sql.append(JOIN_2);
        sql.append(WHERE_JOIN_3);
        sql.append(WHERE_PRODUCT_DONT_DELETE);

        int lastIndexOfWhere = sql.lastIndexOf("WHERE");
        if (sql.indexOf("WHERE") != lastIndexOfWhere) sql.replace(lastIndexOfWhere, lastIndexOfWhere + 5, "AND ");

        return sql.toString();
    }

    /**
     * Lấy ra danh sách sản phẩm theo bộ lọc và quy tắc sắp xếp tương ứng
     *
     * @param mapInfRoot chứa thông tin về mã nhóm danh mục, mã danh mục và mã phân trang
     * @param mapFilter  chứa cặp khóa và giá cho câu điều kiên where
     * @param mapSort    chứa cặp khóa và giá trị sắp xếp
     * @return danh sách săn phẩm từ các thông tin cho trong các map trên
     */
    public List<Product> getProducts(Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort, int limit) {
        List<Product> result;
        int index = 0, page = mapInfRoot.get("page"),
                offset = (page - 1) * limit;
        String select = " p.id, p.name, p.brandName, p.price ";
        String sql = initSQLGetProducts(select, mapInfRoot, mapFilter, mapSort), name;
        sql += LIMIT_OFFSET;
        Handle handle = connector.open();
        Query query = handle.createQuery(sql);
        index = setValuesQuery(query, mapInfRoot, mapFilter, mapSort);
        query.bind(index++, limit);
        query.bind(index, offset);
        result = query.mapToBean(Product.class).list();

        query.close();
        handle.close();

        return result;
    }

    public int totalPages(Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort) {
        int result;
        String select = " COUNT(p.id) ";
        String sql = initSQLGetProducts(select, mapInfRoot, mapFilter, mapSort);
        Handle handle = connector.open();
        Query query = handle.createQuery(sql);
        setValuesQuery(query, mapInfRoot, mapFilter, mapSort);
        result = query.mapTo(Integer.class).findFirst().orElse(0);

        query.close();
        handle.close();

        return result % 20 == 0 ? result / 20 : result / 20 + 1;
    }

    /**
     * khởi tạo câu query theo bộ lọc và quy tắc sắp xếp tương ứng
     *
     * @param select     các trường thông tin muốn lấy.
     * @param mapInfRoot chứa thông tin về mã nhóm danh mục, mã danh mục và mã phân trang
     * @param mapFilter  chứa cặp khóa và giá cho câu điều kiên where
     * @param mapSort    chứa cặp khóa và giá trị sắp xếp
     * @return một câu query hoàn chỉnh chứa các truờng có trong map
     */
    private String initSQLGetProducts(String select, Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort) {
        int idCategoryGroup = 0, idCategory = 0, id = 0;
        idCategory = mapInfRoot.get("id-category");
        idCategoryGroup = mapInfRoot.get("id-category-group");
        StringBuilder sql = new StringBuilder("SELECT " + select + " FROM products AS p ");

        if (idCategoryGroup == 0 && idCategory == 0) {
            sql.append(JOIN_1);
            sql.append(WHERE_JOIN_1);
        }

        if ((idCategoryGroup > 0 && idCategory == 0) || id != 0) {
//            System.out.println("Nhóm danh mục");
            sql.append(JOIN_2);
            sql.append(WHERE_JOIN_2);
        }

        if (idCategoryGroup > 0 && idCategory != 0) {
//            System.out.println("Danh mục");
            sql.append(WHERE_NOT_JOIN);
        }

        sql.append(WHERE_PRODUCT_DONT_DELETE);
        int lastIndexOfWhere = sql.lastIndexOf("WHERE");
        if (sql.indexOf("WHERE") != lastIndexOfWhere) sql.replace(lastIndexOfWhere, lastIndexOfWhere + 5, "AND ");


        sql.append(getSQLFilter(mapFilter));
        sql.append(getSQLSort(mapSort));
        return sql.toString();
    }

    private String getSQLFilter(Map<String, List<String>> mapFilter) {
        StringBuilder sql = new StringBuilder();
        if (mapFilter.isEmpty()) return sql.toString();

        for (Map.Entry<String, List<String>> entry : mapFilter.entrySet()) {
            String name = entry.getKey();
            if (name.equals("com.lamnguyen.mat_kinh_kimi.filter-price")) {
                sql.append(" AND (");
                for (String value : entry.getValue()) {
                    sql.append(MAP_SQL_FILTER.get(name));
                }

                /*Xóa chữ OR cuối*/
                sql.delete(sql.length() - 3, sql.length());
                sql.append(") ");
            } else {
                sql.append(MAP_SQL_FILTER.get(name));
                sql.append("(");
                for (String value : entry.getValue()) {
                    sql.append("?, ");
                }
                sql.delete(sql.length() - 2, sql.length());
                sql.append(")");
            }
        }

        return sql.toString();
    }

    private String getSQLSort(Map<String, String> mapSort) {
        StringBuilder sql = new StringBuilder();
        if (mapSort.isEmpty()) return sql.toString();

        sql.append(" ORDER BY ");
        for (Map.Entry<String, String> entry : mapSort.entrySet()) {
            String name = entry.getKey();
            sql.append(MAP_SQL_SORT.get(name)).append(", ");
        }

        sql.delete(sql.length() - 2, sql.length());
        return sql.toString();
    }

    private int setValuesQuery(Query query, Map<String, Integer> mapInfRoot, Map<String, List<String>> mapFilter, Map<String, String> mapSort) {
        String name;
        StringTokenizer tk;
        int begin, end, index = 0,
                idCategoryGroup = 0,
                idCategory = 0,
                id = 0;
        if (mapInfRoot.containsKey("id")) id = mapInfRoot.get("id");
        if (mapInfRoot.containsKey("id-category-group")) idCategory = mapInfRoot.get("id-category");
        if (mapInfRoot.containsKey("id-category-group")) idCategoryGroup = mapInfRoot.get("id-category-group");


        if (idCategoryGroup > 0 && idCategory == 0) {
            query.bind(index++, idCategoryGroup);
        }

        if (idCategoryGroup > 0 && idCategory > 0) {
            query.bind(index++, idCategory);
        }

        for (Map.Entry<String, List<String>> entry : mapFilter.entrySet()) {
            name = entry.getKey();
            List<String> values = entry.getValue();
            if (name.equals("com.lamnguyen.mat_kinh_kimi.filter-price")) {
                for (String value : values) {
                    tk = new StringTokenizer(value, "-");
                    begin = Integer.parseInt(tk.nextToken());
                    end = Integer.parseInt(tk.nextToken());
                    query.bind(index++, begin);
                    query.bind(index++, end);
                }
            } else {
                for (String value : values) {
                    query.bind(index++, value);
                }
            }
        }

        for (Map.Entry<String, String> entry : mapSort.entrySet()) {
            query.bind(index++, entry.getValue());
        }

        return index;
    }

    /*
    lay danh sach thong tin san pham noi bat tren trang chu
     */
    public List<Product> getInfoProminentProductByStart(int limit) {
        StringBuilder sql = new StringBuilder("SELECT p.id, p.name, p.brandName, p.price FROM products p ");
        sql.append(WHERE_PRODUCT_DONT_DELETE);
        sql.append(LIMIT_OFFSET);
        List<Product> products = connector.withHandle(handle ->
                handle.createQuery(sql.toString())
                        .bind(0, limit)
                        .bind(1, 0)
                        .mapToBean(Product.class).list()
        );
        return products;
    }

    public List<String> getBrands() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT DISTINCT p.brandName " +
                                "FROM products AS p " +
                                "WHERE p.brandName IS NOT NULL  AND `delete` = 0;")
                        .mapTo(String.class)
                        .list()
        );
    }

    public ProductReview getProductReview(int userId, int productId, int modelId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.id AS productId, p.name AS productName, bd.price, bd.quantity,  m.urlImage, m.name AS modelName, m.id AS modelId ")
                .append("FROM bills AS b ")
                .append("JOIN bill_details AS bd ")
                .append("JOIN products AS p ON p.id = bd.productId ")
                .append("JOIN models AS m ON m.id = bd.modelId ")
                .append("ON bd.billId = b.id ")
                .append("JOIN bill_statuses AS bs ON bs.billId = b.id ")
                .append("WHERE bs.`status` LIKE \"%Thành công%\" ")
//                .append("AND p.id NOT IN (SELECT r.productId FROM reviews AS r WHERE r.userId = b.userId AND r.comment IS NOT NULL) ")
                .append("AND bd.modelId = (SELECT MAX(bd2.modelId) FROM bill_details AS bd2 WHERE bd2.id = bd.id) ")
                .append("AND m.id = :modelId ")
                .append("AND p.id = :productId ")
                .append("AND b.userId = :userId;");
        return connector.withHandle(handle ->
                handle.createQuery(sb.toString())
                        .bind("userId", userId)
                        .bind("productId", productId)
                        .bind("modelId", modelId)
                        .mapToBean(ProductReview.class)
                        .findFirst().orElse(null)
        );
    }

//    public ProductReview getProductReview(  int productId, int modelId) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT p.id AS productId, p.name AS productName, bd.price, bd.quantity,  m.urlImage, m.name AS modelName, m.id AS modelId ")
//                .append("FROM bills AS b ")
//                .append("JOIN bill_details AS bd ")
//                .append("JOIN products AS p ON p.id = bd.productId ")
//                .append("JOIN models AS m ON m.id = bd.modelId ")
//                .append("ON bd.billId = b.id ")
//                .append("JOIN bill_statuses AS bs ON bs.billId = b.id ")
//                .append("WHERE bs.`status` LIKE \"%Thành công%\" ")
//                .append("AND p.id NOT IN (SELECT r.productId FROM reviews AS r WHERE r.userId = b.userId AND r.comment IS NOT NULL) ")
//                .append("AND bd.modelId = (SELECT MAX(bd2.modelId) FROM bill_details AS bd2 WHERE bd2.id = bd.id) ")
//                .append("AND m.id = :modelId ")
//                .append("AND p.id = :productId ")
//                .append("AND b.userId = :userId;");
//        return connector.withHandle(handle ->
//                handle.createQuery(sb.toString())
//                        .bind("userId", userId)
//                        .bind("productId", productId)
//                        .bind("modelId", modelId)
//                        .mapToBean(ProductReview.class)
//                        .findFirst().orElse(null)
//        );
//    }

    public List<String> getMaterialsForAdmin() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT DISTINCT p.material " +
                                "FROM products AS p " +
                                "WHERE p.brandName IS NOT NULL;")
                        .mapTo(String.class)
                        .list()
        );
    }

    public List<String> getTypesForAdmin() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT DISTINCT p.type " +
                                "FROM products AS p " +
                                "WHERE p.brandName IS NOT NULL;")
                        .mapTo(String.class)
                        .list()
        );
    }

    public List<String> getMaterials() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT DISTINCT p.material " +
                                "FROM products AS p " +
                                "WHERE p.brandName IS NOT NULL AND `delete` = 0;")
                        .mapTo(String.class)
                        .list()
        );
    }

    public List<String> getTypes() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT DISTINCT p.type " +
                                "FROM products AS p " +
                                "WHERE p.brandName IS NOT NULL AND `delete` = 0;")
                        .mapTo(String.class)
                        .list()
        );
    }

    public int createProductTemp() {
        int id = 0;
        int result = 0;
        while (result == 0) {
            try {
                int productId = nextProductId();
                id = productId;
                result = connector.withHandle(handle ->
                        handle.createUpdate("INSERT INTO products(id, `delete`) " +
                                        "VALUES (?, ?);")
                                .bind(0, productId)
                                .bind(1, 1)
                                .execute()
                );
            } catch (Exception e) {
            }
        }

        return id;
    }

    public int nextProductId() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT MAX(p.id) " +
                                "FROM products AS p")
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        ) + 1;
    }

    public int update(Product product) {
        int result = connector.withHandle(handle -> {
            return handle.createUpdate("UPDATE products " +
                            "SET `categoryId`=?, " +
                            "name = ?, " +
                            "brandName = ?, " +
                            "price = ?, " +
                            "`describe` = ?, " +
                            "material = ?, " +
                            "TYPE = ?, " +
                            "`delete` = ? " +
                            "WHERE id = ?")
                    .bind(0, product.getCategoryId())
                    .bind(1, product.getName())
                    .bind(2, product.getBrandName())
                    .bind(3, product.getPrice())
                    .bind(4, product.getDescribe())
                    .bind(5, product.getMaterial())
                    .bind(6, product.getType())
                    .bind(7, 0)
                    .bind(8, product.getId())
                    .execute();
        });

        return result;
    }

    public boolean delete(int productId) {
        return connector.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM products WHERE id = ?;")
                    .bind(0, productId)
                    .execute();
        }) == 1 ? true : false;
    }

    public boolean lock(int productId) {
        return connector.withHandle(handle -> {
            return handle.createUpdate("UPDATE products SET `delete` = ? WHERE id = ?;")
                    .bind(0, 1)
                    .bind(1, productId)
                    .execute();
        }) == 1 ? true : false;
    }

    public List<Product> getProductForAdmin(String name, int categoryGroupId, int categoryId, String brandName, int available, int limit, int offset) {
        name = getNameFormatForQuery(name);
        String select = "DISTINCT p.id, p.name,p.brandName, c.name AS categoryName, p.price, p.delete ";
        String groupBy = "p.id, p.name,p.brandName, c.name , p.price";
        String sql = getSQLForAdmin(categoryGroupId, categoryId, available, select, groupBy);
        List<Product> result = null;
        Handle handle = connector.open();
        Query query = handle.createQuery(sql);
        if (categoryGroupId == -1 && categoryId == -1) {
            result = query.bind(0, brandName)
                    .bind(1, name)
                    .bind(2, limit)
                    .bind(3, offset)
                    .mapToBean(Product.class).list();
        }

        if (categoryGroupId != -1 && categoryId == -1) {
            result = query.bind(0, brandName)
                    .bind(1, name)
                    .bind(2, categoryGroupId)
                    .bind(3, limit)
                    .bind(4, offset)
                    .mapToBean(Product.class).list();
        }

        if (categoryGroupId == -1 && categoryId != -1) {
            result = query.bind(0, brandName)
                    .bind(1, name)
                    .bind(2, categoryId)
                    .bind(3, limit)
                    .bind(4, offset)
                    .mapToBean(Product.class).list();
        }

        query.close();
        handle.close();
        return result;
    }

    public int totalProduct(String name, int categoryGroupId, int categoryId, String brandName, int available) {
        name = getNameFormatForQuery(name);
        String select = "COUNT(DISTINCT p.id)";
        String groupBy = "";
        String sql = getSQLForAdmin(categoryGroupId, categoryId, available, select, groupBy);
        Handle handle = connector.open();
        Query query = handle.createQuery(sql);
        int result = 0;
        if (categoryGroupId == -1 && categoryId == -1) {
            result = query.bind(0, brandName)
                    .bind(1, name)
                    .bind(2, 9999999)
                    .bind(3, 0)
                    .mapTo(Integer.class).findFirst().orElse(0);
        }

        if (categoryGroupId != -1 && categoryId == -1) {
            result = query.bind(0, brandName)
                    .bind(1, name)
                    .bind(2, categoryGroupId)
                    .bind(3, 9999999)
                    .bind(4, 0)
                    .mapTo(Integer.class).findFirst().orElse(0);
        }

        if (categoryGroupId == -1 && categoryId != -1) {
            result = query.bind(0, brandName)
                    .bind(1, name)
                    .bind(2, categoryId)
                    .bind(3, 9999999)
                    .bind(4, 0)
                    .mapTo(Integer.class).findFirst().orElse(0);
        }

        query.close();
        handle.close();
        return result;
    }

    private String getSQLForAdmin(int categoryGroupId, int categoryId, int available, String select, String groupBy) {
        String stringReplace = "stringReplace";
        String groupByStringReplace = "GROUP BY ";
        StringBuilder sb = new StringBuilder("SELECT ")
                .append(select)
                .append(" FROM products AS p ")
                .append("JOIN categories AS c JOIN category_groups AS cg ON cg.id = c.categoryGroupId ")
                .append("ON c.id = p.categoryId ");
        if (available != 0) {
            sb.append("JOIN models AS m ON m.productId = p.id ")
                    .append("LEFT JOIN bill_details AS bd ON bd.productId = p.id ")
                    .append("WHERE p.brandName LIKE ? ")
                    .append("AND p.name LIKE ? ")
                    .append(stringReplace)
                    .append(groupByStringReplace)
                    .append(" HAVING ")
                    .append("SUM(m.quantity) ");
            if (available > 0) {
                sb.append(">")
                        .append(" SUM(bd.quantity) ")
                        .append(" OR (SUM(m.quantity)")
                        .append(">");
            } else {
                sb.append("<=")
                        .append(" SUM(bd.quantity)")
                        .append(" OR (SUM(m.quantity)")
                        .append("<=");
            }

            sb.append("0 AND SUM(bd.quantity) IS NULL)");
        } else {
            sb.append("WHERE p.brandName LIKE ? ")
                    .append("AND p.name LIKE ? ")
                    .append(stringReplace)
                    .append(groupByStringReplace);
        }
        sb.append(" LIMIT ? OFFSET ?");

        if (groupBy != null && !groupBy.equals(""))
            sb.replace(sb.indexOf(groupByStringReplace), sb.indexOf(groupByStringReplace) + groupByStringReplace.length(), groupByStringReplace + groupBy);
        else
            sb.delete(sb.indexOf(groupByStringReplace), sb.indexOf(groupByStringReplace) + groupByStringReplace.length());
        if (categoryGroupId == -1 && categoryId == -1) {
            sb.delete(sb.indexOf(stringReplace), sb.indexOf(stringReplace) + stringReplace.length());
            return sb.toString();
        }

        if (categoryGroupId != -1) {
            sb.replace(sb.indexOf(stringReplace), sb.indexOf(stringReplace) + stringReplace.length(), "AND cg.id = ? ");
            return sb.toString();
        }

        sb.replace(sb.indexOf(stringReplace), sb.indexOf(stringReplace) + stringReplace.length(), "AND c.id = ? ");
        return sb.toString();
    }

    private String getNameFormatForQuery(String name) {
        StringTokenizer st = new StringTokenizer(name, " ");
        StringBuilder nameSb = new StringBuilder("%");
        while (st.hasMoreTokens()) {
            nameSb.append(st.nextToken());
            if (st.hasMoreTokens()) nameSb.append("%");
        }
        nameSb.append("%");

        return nameSb.toString();
    }

    public boolean unlock(int productId) {
        return connector.withHandle(handle -> {
            return handle.createUpdate("UPDATE products SET `delete` = ? WHERE id = ?;")
                    .bind(0, 0)
                    .bind(1, productId)
                    .execute();
        }) == 1 ? true : false;
    }

    public Product getRecentlyViewedProduct(int productId) {
        return getProductCart(productId);
    }

    public List<ProductReview> getProductReviewsHaveEvaluated(int userId, int offset) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.id AS productId, p.name AS productName, bd.price, bd.quantity,  m.urlImage, m.name AS modelName, m.id AS modelId ")
                .append("FROM bills AS b ")
                .append("JOIN bill_details AS bd ")
                .append("JOIN products AS p ON p.id = bd.productId ")
                .append("JOIN models AS m ON m.id = bd.modelId ")
                .append("ON bd.billId = b.id ")
                .append("JOIN bill_statuses AS bs ON bs.billId = b.id ")
                .append("WHERE bs.`status` LIKE \"%Thành công%\" ")
                .append("AND p.id IN (SELECT r.productId FROM reviews AS r WHERE r.userId = b.userId AND r.comment IS NOT NULL) ")
                .append("AND bd.modelId = (SELECT MAX(bd2.modelId) FROM bill_details AS bd2 WHERE bd2.id = bd.id) ")
                .append("AND b.userId = :userId ")
                .append("LIMIT 8 OFFSET :offset;");
        return connector.withHandle(handle ->
                handle.createQuery(sb.toString())
                        .bind("userId", userId)
                        .bind("offset", offset)
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public List<ProductReview> getProductReviewsNotYetRated(int userId, int offset) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.id AS productId, p.name AS productName, bd.price, bd.quantity,  m.urlImage, m.name AS modelName, m.id AS modelId ")
                .append("FROM bills AS b ")
                .append("JOIN bill_details AS bd ")
                .append("JOIN products AS p ON p.id = bd.productId ")
                .append("JOIN models AS m ON m.id = bd.modelId ")
                .append("ON bd.billId = b.id ")
                .append("JOIN bill_statuses AS bs ON bs.billId = b.id ")
                .append("WHERE bs.`status` LIKE \"%Thành công%\" ")
                .append("AND p.id NOT IN (SELECT r.productId FROM reviews AS r WHERE r.userId = b.userId AND r.comment IS NOT NULL) ")
                .append("AND bd.modelId = (SELECT MAX(bd2.modelId) FROM bill_details AS bd2 WHERE bd2.id = bd.id) ")
                .append("AND b.userId = :userId ")
                .append("LIMIT 8 OFFSET :offset;");
        return connector.withHandle(handle ->
                handle.createQuery(sb.toString())
                        .bind("userId", userId)
                        .bind("offset", offset)
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public List<Product> getSearchProducts(String name) {
        String sql = "SELECT p.id, p.name, p.brandName FROM products AS p WHERE `delete` = 0 AND (";
        Handle handle;
        Query query;
        StringTokenizer tk = new StringTokenizer(name, " ");
        int totalTk = tk.countTokens();
        for (int i = 0; i < totalTk; i++)
            sql += "p.name LIKE ? OR ";
        if (totalTk == 0) {
            sql = sql.substring(0, sql.length() - 5);
        } else {
            sql = sql.substring(0, sql.length() - 4);
            sql += ");";
        }
        handle = connector.open();
        query = handle.createQuery(sql);
        for (int i = 0; i < totalTk; i++)
            query.bind(i, "%" + tk.nextToken() + "%");
        List<Product> products = query.mapToBean(Product.class).list();
        query.close();
        handle.close();
        return products;
    }

    public List<Product> getProductsIdAndName() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT p.id, p.name FROM products AS p")
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public void update() {
        String sql = "UPDATE products SET price = :price WHERE id = :id";
        Random rd = new Random(System.currentTimeMillis());
        for (int i = 1; i <= 372; i++) {
            int id = i;
            double price = rd.nextDouble(0, 10000000);
            connector.withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("price", price)
                            .bind("id", id)
                            .execute()
            );
        }
    }

    public static void main(String[] args) {
        ProductRepositoryImpl productDAO = new ProductRepositoryImpl();
        productDAO.update();
    }
}
