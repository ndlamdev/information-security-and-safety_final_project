/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:08 AM - 15/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerifyService {
    UserService userService = UserService.getInstance();

    private static VerifyService instance;

    private VerifyService() {

    }

    public static VerifyService getInstance() {
        return (instance = instance != null ? instance : new VerifyService());
    }

    private PublicKey loadPublicKey(int userId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String base64PublicKey = userService.getPublicKey(userId);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey));
        return KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
    }

    public boolean  verifyBill(int userId, String algorithm, String signature, String pathFile) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        var signatureObj = Signature.getInstance(algorithm + "withDSA");
        signatureObj.initVerify(loadPublicKey(userId));
        signVerifyHelper(signatureObj, pathFile);
        return signatureObj.verify(Base64.getDecoder().decode(signature));
    }

    private void signVerifyHelper(Signature signature, String file) throws IOException, SignatureException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[1024 * 10];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) signature.update(buffer, 0, bytesRead);
        bis.close();
    }
}
