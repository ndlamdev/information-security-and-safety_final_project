package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.NumberList;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class DashBoardRepositoryImpl extends Repository {
    private static DashBoardRepositoryImpl instance;

    public static DashBoardRepositoryImpl getInstance() {
        return (instance = instance == null ? new DashBoardRepositoryImpl() : instance);
    }

    public List<NumberList> numberListByCategoryGroupAndMonth(int year) {
        String sql = """
                SELECT cg.name AS name, MONTH(bs.date) AS TIME, SUM(bd.quantity) AS quantity
                FROM bill_details AS bd
                         JOIN products AS p ON p.id = bd.productId
                         JOIN (categories AS c JOIN category_groups AS cg ON cg.id = c.categoryGroupId) ON c.id = p.categoryId
                         JOIN bill_statuses AS bs ON bs.billId = bd.billId
                WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
                  AND YEAR(bs.date) = :year
                GROUP BY cg.name, TIME;
                """;
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByCategoryAndMonth(int year) {
        String sql = """
                SELECT c.name AS name, MONTH(bs.date) AS TIME, SUM(bd.quantity) AS quantity
                FROM bill_details AS bd
                         JOIN products AS p ON p.id = bd.productId
                         JOIN (categories AS c JOIN category_groups AS cg ON cg.id = c.categoryGroupId) ON c.id = p.categoryId
                         JOIN bill_statuses AS bs ON bs.billId = bd.billId
                WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
                  AND YEAR(bs.date) = :year
                GROUP BY c.name, TIME;
                """;
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByCategoryGroupAndQuarter(int year) {
        String sql = """
                SELECT cg.name          AS name,
                       CASE
                           WHEN MONTH(bs.date) IN (1, 2, 3) THEN 1
                           WHEN MONTH(bs.date) IN (4, 5, 6) THEN 2
                           WHEN MONTH(bs.date) IN (7, 8, 9) THEN 3
                           ELSE 4
                           END          AS TIME,
                       SUM(bd.quantity) AS quantity
                FROM bill_details AS bd
                         JOIN products AS p ON p.id = bd.productId
                         JOIN (categories AS c JOIN category_groups AS cg ON cg.id = c.categoryGroupId) ON c.id = p.categoryId
                         JOIN bill_statuses AS bs ON bs.billId = bd.billId
                WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
                  AND YEAR(bs.date) = :year
                GROUP BY cg.name, TIME;
                """;
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByCategoryAndQuarter(int year) {
        String sql = """
                SELECT c.name           AS name,
                       CASE
                           WHEN MONTH(bs.date) IN (1, 2, 3) THEN 1
                           WHEN MONTH(bs.date) IN (4, 5, 6) THEN 2
                           WHEN MONTH(bs.date) IN (7, 8, 9) THEN 3
                           ELSE 4
                           END          AS TIME,
                       SUM(bd.quantity) AS quantity
                FROM bill_details AS bd
                         JOIN products AS p ON p.id = bd.productId
                         JOIN (categories AS c JOIN category_groups AS cg ON cg.id = c.categoryGroupId) ON c.id = p.categoryId
                         JOIN bill_statuses AS bs ON bs.billId = bd.billId
                WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
                  AND YEAR(bs.date) = :year
                GROUP BY c.name, TIME;
                """;
        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("year", year)
                        .mapToBean(NumberList.class)
                        .list()
        );
    }

    public List<NumberList> numberListByProduct(int productId, int month, int year) {
        String sql = """
                SELECT p.name AS name, DAY(bs.date) AS TIME, SUM(bd.quantity) AS quantity
                FROM bills AS b
                         JOIN bill_statuses AS bs ON bs.billId = b.id
                         JOIN (bill_details AS bd JOIN products AS p ON p.id = bd.id)
                              ON bd.billId = b.id
                WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = b.id)
                  AND YEAR(bs.date) = :year
                  AND MONTH(bs.date) = :month
                  AND p.id = :productId
                GROUP BY p.name, TIME
                """;
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