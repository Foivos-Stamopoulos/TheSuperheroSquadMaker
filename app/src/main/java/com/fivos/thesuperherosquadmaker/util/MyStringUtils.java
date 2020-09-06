package com.fivos.thesuperherosquadmaker.util;

public class MyStringUtils {

    public static boolean isNoE(final String s) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

}
