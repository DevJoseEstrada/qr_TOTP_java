package com.devjmestrada.utils;

import com.google.zxing.common.BitMatrix;

import static java.util.Objects.requireNonNull;

public class QRPrinter {

    public static final String BLACK = "\033[40m  \033[0m";
    public static final String WHITE = "\033[47m  \033[0m";

    public static void printQR(BitMatrix matrix) {
        requireNonNull(matrix, "Missing argument: matrix");
        System.out.println( matrix.toString(BLACK, WHITE));
    }
}
