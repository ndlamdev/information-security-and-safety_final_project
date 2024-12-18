package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.time.LocalDateTime;

public class PublicKeyRepositoryImpl extends Repository {
    private static PublicKeyRepositoryImpl instance;

    private PublicKeyRepositoryImpl() {
    }

    public static PublicKeyRepositoryImpl getInstance() {
        return (instance = instance != null ? instance : new PublicKeyRepositoryImpl());
    }

    public boolean uploadPublicKey(String hashKey, int userId) {
        return connector.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO public_keys (hashKey, uploadTime, userId) VALUES (?, ?, ?)")
                    .bind(0, hashKey)
                    .bind(1, LocalDateTime.now())
                    .bind(2, userId)
                    .execute() > 0;
        });
    }

    public boolean lockPublicKey(int userId) {
        return connector.withHandle(handle -> {
            return handle.createUpdate("UPDATE public_keys SET expired = ? WHERE userId = ?")
                    .bind(0, LocalDateTime.now())
                    .bind(1, userId)
                    .execute() > 0;
        });
    }

    public boolean existsPublicKey(int userId) {
        return connector.withHandle(handle -> {
            return handle.createQuery("SELECT COUNT(*) FROM public_keys WHERE userId = ? AND expired IS NULL")
                    .bind(0, userId)
                    .mapTo(Integer.class)
                    .one() > 0;
        });
    }

    public void updateCodeVerify(String code, int userId) {
        connector.withHandle(handle ->
                handle.createUpdate("UPDATE public_keys SET " +
                                "verify = ? " +
                                "WHERE userId = ?;")
                        .bind(0, code)
                        .bind(1, userId)
                        .execute()
        );
    }
}
