package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillManage;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;
import java.util.Random;

public class BillRepositoryImpl extends Repository {
    private static BillRepositoryImpl instance;

    private BillRepositoryImpl() {
    }

    public static BillRepositoryImpl getInstance() {
        return (instance = instance == null ? new BillRepositoryImpl() : instance);
    }

    public int insert(Bill bill) {
        int transfer = bill.isTransfer() ? 1 : 0;
        int billId = getNextId();
        connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO bills(id, userId, userName, email, phoneNumber, codeProvince, codeDistrict, codeWard, address, transportFee, transfer, verify) " +
                                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                        .bind(0, billId)
                        .bind(1, bill.getUserId())
                        .bind(2, bill.getUserName())
                        .bind(3, bill.getEmail())
                        .bind(4, bill.getPhoneNumber())
                        .bind(5, bill.getCodeProvince())
                        .bind(6, bill.getCodeDistrict())
                        .bind(7, bill.getCodeWard())
                        .bind(8, bill.getAddress())
                        .bind(9, bill.getTransportFee())
                        .bind(10, transfer)
                        .bind(11, bill.getVerify())
                        .execute()
        );

        return billId;
    }

    public int getNextId() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT MAX(b.id) FROM bills as b;")
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        ) + 1;
    }

    public boolean bought(int userId, int productId, int modelId) {
        int count = connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT COUNT(bd.id)
                                FROM bills AS b
                                JOIN bill_details AS bd ON bd.billId = b.id
                                WHERE
                                b.userId = ? AND bd.productId = ? AND bd.modelId = ?
                                """)
                        .bind(0, userId)
                        .bind(1, productId)
                        .bind(2, modelId)
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        );

        return count != 0;
    }

    public List<Bill> getBillsByUserId(int userId, String status, int offset) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT b.id, b.userId, b.userName, b.email, b.transfer
                                FROM bills AS b
                                JOIN bill_statuses AS bs ON bs.billId = b.id
                                WHERE bs.id in (SELECT MAX(bs2.id) FROM bill_statuses AS bs2 WHERE bs2.billId = b.id)
                                AND bs.`status` LIKE :status
                                AND b.userId = :userId
                                ORDER BY b.id ASC
                                LIMIT 8 OFFSET :offset;
                                """)
                        .bind("status", "%" + status + "%")
                        .bind("userId", userId)
                        .bind("offset", offset)
                        .mapToBean(Bill.class)
                        .list());
    }

    public Bill getBill(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT b.id,
                                       b.userId,
                                       b.userName,
                                       b.email,
                                       b.address,
                                       b.phoneNumber,
                                       b.transfer,
                                       b.transportFee,
                                       b.codeProvince,
                                       b.codeDistrict,
                                       b.codeWard
                                FROM bills AS b
                                WHERE b.id = :id;
                                """)
                        .bind("id", billId)
                        .mapToBean(Bill.class)
                        .findFirst().orElse(null));
    }

    public int updateContact(Bill bill) {
        var count = connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT COUNT(*)
                                FROM bills b
                                WHERE b.id = :id
                                AND b.userName = :userName
                                AND b.phoneNumber = :phoneNumber
                                AND b.email = :email
                                AND b.address = :address
                                AND b.codeProvince = :codeProvince
                                AND b.codeDistrict = :codeDistrict
                                AND b.codeWard = :codeWard
                                """)
                        .bind("userName", bill.getUserName())
                        .bind("email", bill.getEmail())
                        .bind("phoneNumber", bill.getPhoneNumber())
                        .bind("address", bill.getAddress())
                        .bind("codeProvince", bill.getCodeProvince())
                        .bind("codeDistrict", bill.getCodeDistrict())
                        .bind("codeWard", bill.getCodeWard())
                        .bind("id", bill.getId())
                        .mapTo(Integer.class)
                        .findFirst().orElse(-9999)
        );

        if (count >= 1)
            return 0;


        var result = connector.withHandle(handle ->
                handle.createUpdate("""
                                UPDATE bills SET
                                userName = :userName,
                                email = :email,
                                phoneNumber = :phoneNumber,
                                address = :address,
                                codeProvince = :codeProvince,
                                codeDistrict = :codeDistrict,
                                codeWard = :codeWard
                                WHERE id = :billId;
                                """
                        )
                        .bind("userName", bill.getUserName())
                        .bind("email", bill.getEmail())
                        .bind("phoneNumber", bill.getPhoneNumber())
                        .bind("address", bill.getAddress())
                        .bind("codeProvince", bill.getCodeProvince())
                        .bind("codeDistrict", bill.getCodeDistrict())
                        .bind("codeWard", bill.getCodeWard())
                        .bind("billId", bill.getId())
                        .execute());

        return result == 1 ? result : -1;
    }

    public List<BillManage> getBillManage(String billId, String userName, String billStatus, int limit, int offset) {
        StringBuilder sqlSB = new StringBuilder("SELECT DISTINCT b.id AS billId, b.userId AS userId, bs.`status` AS status, b.userName AS name, b.email AS email, b.transfer AS transfer\n")
                .append("FROM bills AS b\n")
                .append("JOIN bill_details AS bd ON bd.billId = b.id\n")
                .append("JOIN bill_statuses AS bs ON bs.billId = b.id\n")
                .append("WHERE bs.id = (SELECT MAX(bill_statuses.id) FROM bill_statuses WHERE bill_statuses.billId = b.id)\n");
        if (billId != null) sqlSB.append("AND CAST(bs.id AS CHAR(11)) LIKE :billId\n");
        if (userName != null) sqlSB.append("AND b.userName LIKE :userName\n");
        if (billStatus != null) sqlSB.append("AND bs.status LIKE :status\n");
        sqlSB.append("LIMIT :limit OFFSET :offset;");
        return connector.withHandle(handle ->
                handle.createQuery(sqlSB.toString())
                        .bind("billId", "%" + billId + "%")
                        .bind("userName", "%" + userName + "%")
                        .bind("status", "%" + billStatus + "%")
                        .bind("limit", limit)
                        .bind("offset", offset)
                        .mapToBean(BillManage.class)
                        .list()
        );
    }

    public int totalBillManage(String billId, String userName, String billStatus) {
        StringBuilder sqlSB = new StringBuilder("SELECT COUNT(*)\n")
                .append("FROM bills AS b\n")
                .append("JOIN bill_details AS bd ON bd.billId = b.id\n")
                .append("JOIN bill_statuses AS bs ON bs.billId = b.id\n")
                .append("WHERE bs.id = (SELECT MAX(bill_statuses.id) FROM bill_statuses WHERE bill_statuses.billId = b.id)\n");
        if (billId != null) sqlSB.append("AND CAST(bs.id AS CHAR(11)) LIKE :billId\n");
        if (userName != null) sqlSB.append("AND b.userName LIKE :userName\n");
        if (billStatus != null) sqlSB.append("AND bs.status LIKE :status\n");
        return connector.withHandle(handle ->
                handle.createQuery(sqlSB.toString())
                        .bind("billId", "%" + billId + "%")
                        .bind("userName", "%" + userName + "%")
                        .bind("status", "%" + billStatus + "%")
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        );
    }

    public boolean exists(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                select b.id
                                from bills b
                                where b.id = ?;
                                """)
                        .bind(0, billId)
                        .mapTo(Integer.class)
                        .findFirst().orElse(-9999)
        ) != -9999;
    }

    public Bill findByUserIdAndId(int userId, int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT b.id,
                                       b.userId,
                                       b.userName,
                                       b.email,
                                       b.address,
                                       b.phoneNumber,
                                       b.transfer,
                                       b.transportFee,
                                       b.codeProvince,
                                       b.codeDistrict,
                                       b.codeWard,
                                       b.dateTimeSign
                                FROM bills AS b
                                WHERE b.id = :id
                                AND b.userId = :userId;
                                """)
                        .bind("id", billId)
                        .bind("userId", userId)
                        .mapToBean(Bill.class)
                        .findFirst().orElse(null));
    }

    public int updateSignature(Integer id, Signature signature) {
        return connector.withHandle(handle ->
                handle.createUpdate("""
                                UPDATE bills b
                                set b.algorithm = :algorithm,
                                b.signature = :signature,
                                b.verify = :verify,
                                b.dateTimeSign = NOW()
                                WHERE b.id = :id;
                                """)
                        .bind("id", id)
                        .bind("algorithm", signature.getAlgorithm())
                        .bind("signature", signature.getSignature())
                        .bind("verify", signature.getVerify())
                        .execute()
        );
    }

    public Signature findSignature(Integer id) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT
                                b.algorithm as algorithm,
                                b.signature as signature,
                                b.verify as verify
                                FROM bills AS b
                                WHERE b.id = :id;
                                """)
                        .bind("id", id)
                        .mapToBean(Signature.class)
                        .findFirst().orElse(null));
    }

    public int updateVerify(int billId, boolean verify) {
        return connector.withHandle(handle ->
                handle.createUpdate("""
                                UPDATE bills b
                                   set b.verify = :verify
                                WHERE b.id = :id;
                                """)
                        .bind("verify", verify)
                        .bind("id", billId)
                        .execute()
        );
    }

    public boolean isVerify(int billId) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT
                                    b.verify
                                FROM bills AS b
                                WHERE b.id = :id;
                                """)
                        .bind("id", billId)
                        .mapTo(Boolean.class)
                        .findFirst().orElse(true));
    }
}
