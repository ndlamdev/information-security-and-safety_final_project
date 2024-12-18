package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.PublicKeyRepositoryImpl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

public class PublicKeyService {
    private static PublicKeyService instance;
    private final PublicKeyRepositoryImpl PUBLIC_KEY_REPOSITORY;

    private PublicKeyService() {
        PUBLIC_KEY_REPOSITORY = PublicKeyRepositoryImpl.getInstance();
    }

    public static PublicKeyService getInstance() {
        return (instance = instance != null ? instance : new PublicKeyService());
    }

    public boolean uploadPublicKey(PublicKey key, int userId) {
        return PUBLIC_KEY_REPOSITORY.uploadPublicKey(Base64.getEncoder().encodeToString(key.getEncoded()), userId);
    }

    public boolean lockPublicKey(int userId) {
        return PUBLIC_KEY_REPOSITORY.lockPublicKey(userId);
    }

    public boolean existsPublicKey(int userId) {
        return PUBLIC_KEY_REPOSITORY.existsPublicKey(userId);
    }

    public PublicKey getPublicKey(int userId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var base64 = PUBLIC_KEY_REPOSITORY.publicKey(userId);
        if (base64 == null) return null;
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY_REPOSITORY.publicKey(userId)));
        return KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
    }

    /**
     * Đọc khóa công khai từ file nguồn
     *
     * @param inputStream stream data key
     * @return PublicKey khóa công khai đọc được
     */
    public static PublicKey readPublicKey(InputStream inputStream) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(inputStream, 1024 * 10));
        String algorithm = input.readUTF();
        String base64PublicKey = input.readUTF();
        input.close();
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey));
        return KeyFactory.getInstance(algorithm).generatePublic(x509EncodedKeySpec);
    }
}
