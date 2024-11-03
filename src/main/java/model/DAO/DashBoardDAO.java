package model.DAO;

import model.bean.NumberList;

import java.util.List;

public class DashBoardDAO extends DAO {
    private static DashBoardDAO service;

    public static DashBoardDAO getInstance() {
        return (service = service == null ? new DashBoardDAO() : service);
    }

    public void numberByCategory() {
//        connector.withHandle(handle ->
//                handle.createQuery("")
//                        .map
//        );
    }

    public List<NumberList> numberListByCategoryGroupAndMonth(int year) {
        String sql = "SELECT cg.name AS name, MONTH(bs.date) AS TIME, SUM(bd.quantity) AS quantity FROM bill_details AS bd\n" +
                "JOIN products AS p \n" +
                "\tJOIN categories AS c\n" +
                "\t\tJOIN category_groups AS cg ON cg.id = c.categoryGroupId\n" +
                "\tON c.id = p.categoryId\n" +
                "ON p.id = bd.productId\n" +
                "JOIN bill_statuses AS bs ON bs.billId = bd.billId\n" +
                "WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)\n" +
                "AND YEAR(bs.date) = :year\n" +
                "GROUP BY cg.name, MONTH(bs.date);";
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByCategoryAndMonth(int year) {
        String sql = "SELECT c.name AS name, MONTH(bs.date) AS TIME, SUM(bd.quantity) AS quantity FROM bill_details AS bd\n" +
                "JOIN products AS p \n" +
                "\tJOIN categories AS c\n" +
                "\t\tJOIN category_groups AS cg ON cg.id = c.categoryGroupId\n" +
                "\tON c.id = p.categoryId\n" +
                "ON p.id = bd.productId\n" +
                "JOIN bill_statuses AS bs ON bs.billId = bd.billId\n" +
                "WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)\n" +
                "AND YEAR(bs.date) = :year\n" +
                "GROUP BY c.name, MONTH(bs.date);";
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByCategoryGroupAndQuarter(int year) {
        String sql = "SELECT \n" +
                "\tcg.name AS name,\n" +
                "\tCASE\n" +
                "\t\tWHEN MONTH(bs.date) IN (1, 2, 3) THEN 1\n" +
                "\t\tWHEN MONTH(bs.date) IN (4, 5, 6) THEN 2\n" +
                "\t\tWHEN MONTH(bs.date) IN (7, 8, 9) THEN 3\n" +
                "\t\tELSE 4\n" +
                "\tEND AS TIME, \n" +
                "\tSUM(bd.quantity) AS quantity \n" +
                "FROM bill_details AS bd\n" +
                "JOIN products AS p\n" +
                "\tJOIN categories AS c\n" +
                "\t\tJOIN category_groups AS cg ON cg.id = c.categoryGroupId\n" +
                "\tON c.id = p.categoryId\n" +
                "ON p.id = bd.productId\n" +
                "JOIN bill_statuses AS bs ON bs.billId = bd.billId\n" +
                "WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)\n" +
                "AND YEAR(bs.date) = :year\n" +
                "GROUP BY cg.name, MONTH(bs.date) IN (1, 2, 3), MONTH(bs.date) IN (4, 5, 6), MONTH(bs.date) IN (7, 8, 9), MONTH(bs.date) IN (10, 11, 12);";
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByCategoryAndQuarter(int year) {
        String sql = "SELECT \n" +
                "\tc.name AS name,\n" +
                "\tCASE\n" +
                "\t\tWHEN MONTH(bs.date) IN (1, 2, 3) THEN 1\n" +
                "\t\tWHEN MONTH(bs.date) IN (4, 5, 6) THEN 2\n" +
                "\t\tWHEN MONTH(bs.date) IN (7, 8, 9) THEN 3\n" +
                "\t\tELSE 4 \n" +
                "\tEND AS TIME, \n" +
                "\tSUM(bd.quantity) AS quantity \n" +
                "FROM bill_details AS bd\n" +
                "JOIN products AS p\n" +
                "\tJOIN categories AS c\n" +
                "\t\tJOIN category_groups AS cg ON cg.id = c.categoryGroupId\n" +
                "\tON c.id = p.categoryId\n" +
                "ON p.id = bd.productId\n" +
                "JOIN bill_statuses AS bs ON bs.billId = bd.billId\n" +
                "WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)\n" +
                "AND YEAR(bs.date) = :year\n" +
                "GROUP BY c.name, MONTH(bs.date) IN (1, 2, 3), MONTH(bs.date) IN (4, 5, 6), MONTH(bs.date) IN (7, 8, 9), MONTH(bs.date) IN (10, 11, 12);";
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByProduct(int productId, int month, int year) {
        String sql = "SELECT p.name AS name, DAY(bs.date) AS TIME, SUM(bd.quantity) AS quantity\n" +
                "FROM bills AS b\n" +
                "JOIN bill_details AS bd\n" +
                "\tJOIN products AS p ON p.id = bd.id\n" +
                "ON bd.billId = b.id\n" +
                "JOIN bill_statuses AS bs ON bs.billId = b.id\n" +
                "WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = b.id)\n" +
                "AND YEAR(bs.date) = :year AND MONTH(bs.date) = :month AND p.id = :productId\n" +
                "GROUP BY p.name, DAY(bs.date)";
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("productId", productId)
                        .bind("month", month)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<String> listCategoryName() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT c.name FROM categories AS c")
                        .mapTo(String.class)
                        .list()
        );
    }

    public List<String> listCategoryGroupName() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT cg.name FROM category_groups AS cg")
                        .mapTo(String.class)
                        .list()
        );
    }
}