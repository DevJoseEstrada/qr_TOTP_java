package org.devjmestrada.otpsystem;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.devjmestrada.otpsystem.model.OtpAuth;
import org.devjmestrada.otpsystem.services.OTP.OTPChecker;
import org.devjmestrada.otpsystem.utils.SecretKey;

import java.util.Map;

import static org.devjmestrada.otpsystem.utils.QRPrinter.printQR;

public class Main {
    public final static int QR_SIZE = 5;
    public final static String USER_NAME = "Pablo";
    public final static String ISSUER = "MasterCiber";
    public final static String ALGORITHM = "SHA1";
    public final static int DIGITS = 6;
    public final static int PERIOD = 30;

    public static void main(String[] args) throws WriterException {
        Boolean codeIsValid;
        String otp;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String secretKey = SecretKey.generateSecretKey();
        OtpAuth otpAuth = OtpAuth.builder().userName(USER_NAME).secret(secretKey).issuer(ISSUER).algorithm(ALGORITHM).digits(DIGITS).period(PERIOD).build();
        System.out.println("The users secret is: " + otpAuth.getSecret());
        System.out.println("Scan the following QR with Google Auth ");
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuth.buildURI(), BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE, Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L));
        printQR(bitMatrix);
        do {
        System.out.println("Introduce the OTP code to verify");
        otp = System.console().readLine();
        codeIsValid = OTPChecker.isValidCode(secretKey,otp)
        if(codeIsValid){
            System.out.println("The OTP is correct");
        }else {
            System.out.println("The OTP is incorrect");
        }
        } while (codeIsValid == false);
        
    }
}
