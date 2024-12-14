package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Random;

public class BillStatusRepositoryImpl extends Repository {
    private static BillStatusRepositoryImpl instance;

    private BillStatusRepositoryImpl() {
    }

    public static BillStatusRepositoryImpl getInstance() {
        return (instance = instance != null ? instance : new BillStatusRepositoryImpl());
    }

    /*
    lay thong in trang thai
     */
    public List<BillStatus> getInfStatus(int billId) {
        List<BillStatus> result = connector.withHandle(handle ->
                handle.createQuery("SELECT bs.status, bs.date FROM bill_statuses bs WHERE bs.billId = :billId")
                        .bind("billId", billId)
                        .mapToBean(BillStatus.class).list()
        );
        return result;
    }

    public BillStatus getInfDateStatus(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT bs.id, bs.billId, bs.status, bs.date FROM bill_statuses bs WHERE bs.billId = ?")
                        .bind(0, billId)
                        .mapToBean(BillStatus.class).findFirst().orElse(null)
        );
    }

    public void insert(BillStatus status) {
        connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO bill_statuses(billId, `date`, `status`, `describe`, canEdit) VALUES (?, ?, ?, ?, ?)")
                        .bind(0, status.getBillId())
                        .bind(1, status.getDate())
                        .bind(2, status.getStatus())
                        .bind(3, status.getDescribe())
                        .bind(4, status.getCanEdit())
                        .execute());
    }

    public List<BillStatus> getBillStatus(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT bs.billId, bs.status, bs.date, bs.describe, bs.canEdit
                                FROM bill_statuses bs
                                WHERE bs.billId = :billId
                                ORDER BY bs.id;
                                """)
                        .bind("billId", billId)
                        .mapToBean(BillStatus.class).list()
        );
    }

    public List<BillStatus> getLastBillStatus(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT bs.status, bs.date, bs.describe, bs.canEdit
                                FROM bill_statuses bs
                                WHERE bs.billId = :billId
                                AND bs.id = (SELECT MAX(bs2.id) FROM bill_statuses bs2 WHERE bs2.billId = bs.billId);
                                """)
                        .bind("billId", billId)
                        .mapToBean(BillStatus.class)
                        .list()
        );
    }

    public LocalDateTime getDateOrderBill(int billId) {
        String sql = """
                SELECT bs.date
                FROM bill_statuses AS bs
                WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = :bsBillId);
                """;

        return connector.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("billId", billId)
                        .bind("bsBillId", billId)
                        .mapTo(LocalDateTime.class)
                        .findFirst().orElse(null)
        );
    }

    public void insertSampleData() {
        Random rd = new Random();
        for (int i = 1; i <= 100; i++) {
            int id = i;
            int billId = i;
            int year = 2023;
            int month = rd.nextInt(1, 13);
            int day = rd.nextInt(1, YearMonth.of(year, month).lengthOfMonth() + 1);
            int hour = rd.nextInt(0, 23);
            int minute = rd.nextInt(1, 60);
            int second = rd.nextInt(1, 60);
            connector.withHandle(handle ->
                    handle.createUpdate("INSERT INTO bill_statuses(id, billId, `date`, `status`, `describe`, canEdit) VALUES (?, ?, ?, ?, ?, ?)")
                            .bind(0, id)
                            .bind(1, billId)
                            .bind(2, LocalDateTime.of(year, month, day, hour, minute, second))
                            .bind(3, "Chờ xác nhận")
                            .bind(4, "Đơn hàng đang chờ xét duyệt!")
                            .bind(5, 1)
                            .execute());
        }
    }

    public int isCancel(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                    SELECT bs.id
                                    FROM bill_statuses bs
                                    WHERE bs.billId = :billId
                                    AND bs.status = 'Đã hủy'
                                """)
                        .bind("billId", billId)
                        .mapTo(Integer.class)
                        .findFirst().orElse(-999)
        );
    }

    public void remove(int id) {
        connector.withHandle(handle ->
                handle.createUpdate("""
                                    DELETE FROM bill_statuses bs
                                    WHERE bs.id = :id;
                                """)
                        .bind("id", id)
                        .execute()
        );
    }
}
