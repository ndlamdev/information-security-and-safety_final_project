/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:08 AM - 15/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.service.impl.VerifySignatureFileImpl;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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


    public boolean verifyBill(int userId, String algorithm, String signature, String pathFile) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        var signatureObj = VerifySignatureFileImpl.getInstance(algorithm, PublicKeyService.getInstance().getPublicKey(userId));
        return signatureObj.verifyFile(pathFile, signature);
    }
}
