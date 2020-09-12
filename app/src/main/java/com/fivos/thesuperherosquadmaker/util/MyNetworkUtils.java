package com.fivos.thesuperherosquadmaker.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyNetworkUtils {

    /**
     * Checks device connectivity
     * @param context
     * @return if device is connected to the Internet
     */
    public static boolean isConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
