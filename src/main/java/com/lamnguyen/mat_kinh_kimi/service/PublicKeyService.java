package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.PublicKeyRepositoryImpl;

import java.time.LocalDateTime;

public class PublicKeyService {
    private static PublicKeyService instance;
    private final PublicKeyRepositoryImpl PUBLIC_KEY_REPOSITORY;

    private PublicKeyService() {
        PUBLIC_KEY_REPOSITORY = PublicKeyRepositoryImpl.getInstance();
    }

    public static PublicKeyService getInstance() {
        return (instance = instance != null ? instance : new PublicKeyService());
    }

    public boolean uploadPublicKey(String hashKey, int userId) {
        return PUBLIC_KEY_REPOSITORY.uploadPublicKey(hashKey, userId);
    }

    public boolean lockPublicKey(int userId, LocalDateTime dateTime) {
        return PUBLIC_KEY_REPOSITORY.lockPublicKey(userId, dateTime);
    }
}
