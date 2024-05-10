package org.devjmestrada.otpsystem.services.OTP;


import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OTPGenerator {
    private static final String ALGORITHM = "HmacSHA1";
    private static final int DIGITS = 6;


    public static String generate(String key, long counter) throws NoSuchAlgorithmException, InvalidKeyException {

        byte[] hash = generateHash(key, counter);
        return getDigitsFromHash(hash);

    }

    private static byte[] generateHash(String key, long counter) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = counter;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(key);
        SecretKeySpec signKey = new SecretKeySpec(decodedKey, ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(signKey);
        return mac.doFinal(data);
    }

    private static String getDigitsFromHash(byte[] hash) {
        int offset = hash[hash.length - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= Math.pow(10, DIGITS);
        return String.format("%0" + DIGITS + "d", truncatedHash);
    }
}
