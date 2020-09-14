package com.fivos.thesuperherosquadmaker.util;

/**
 * Return the error code from the network request that failed
 */
public class ParseResponse {

    public static int getStatusCode(Throwable e) {
        try {
            return ((retrofit2.HttpException) e).code();
        } catch (Exception ex) {
            return -1;
        }
    }

}
