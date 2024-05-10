package com.devjmestrada.services.OTP;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class OTPChecker {

    private final static int TIME_PERIOD = 30;
    private static int ALLOWED_TIME_PERIOD_DISCREPANCY = 1;


    public static boolean isValidCode(String secret, String code) {
        long currentBucket = Math.floorDiv(Instant.now().getEpochSecond(), TIME_PERIOD);
        boolean success = false;
        for (int i = -ALLOWED_TIME_PERIOD_DISCREPANCY; i <= ALLOWED_TIME_PERIOD_DISCREPANCY; i++) {
            success = checkCode(secret, currentBucket + i, code) || success;
        }

        return success;
    }

    private static boolean checkCode(String secret, long counter, String code) {
        try {
            String actualCode = OTPGenerator.generate(secret, counter);
            return timeSafeStringComparison(actualCode, code);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }

    private static boolean timeSafeStringComparison(String a, String b) {
        byte[] aBytes = a.getBytes();
        byte[] bBytes = b.getBytes();

        if (aBytes.length != bBytes.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < aBytes.length; i++) {
            result |= aBytes[i] ^ bBytes[i];
        }

        return result == 0;
    }
}
