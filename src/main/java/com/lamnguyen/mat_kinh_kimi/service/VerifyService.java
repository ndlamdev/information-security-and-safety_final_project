/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:08 AM - 15/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.service.impl.VerifySignatureFileImpl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public class VerifyService {
    private static VerifyService instance;

    private VerifyService() {

    }

    public static VerifyService getInstance() {
        return (instance = instance != null ? instance : new VerifyService());
    }


    public boolean verifyBill(int userId, String algorithm, String signature, String pathFile) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        var signatureObj = VerifySignatureFileImpl.getInstance(algorithm, PublicKeyService.getInstance().getPublicKey(userId));
        return signatureObj.verifyFile(pathFile, signature);
    }
}
