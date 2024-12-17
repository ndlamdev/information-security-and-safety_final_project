/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:28 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.service.impl;

import com.lamnguyen.mat_kinh_kimi.service.ISignAndVerifyHelper;
import com.lamnguyen.mat_kinh_kimi.service.IVerifySignature;

import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class VerifySignatureFileImpl implements IVerifySignature {
    private static Signature signatureObj;

    private VerifySignatureFileImpl(String algorithm, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException {
        signatureObj = Signature.getInstance(algorithm);
        signatureObj.initVerify(key);
    }

    public static IVerifySignature getInstance(String algorithm, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new VerifySignatureFileImpl(algorithm + "withDSA", key);
    }

    @Override
    public boolean verifyFile(String source, String signature) throws IOException, SignatureException {
        ISignAndVerifyHelper.signVerifyHelper(signatureObj, source);
        return signatureObj.verify(Base64.getDecoder().decode(signature));
    }
}
