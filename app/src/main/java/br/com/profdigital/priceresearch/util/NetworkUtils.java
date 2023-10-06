package br.com.profdigital.priceresearch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtils {
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mActiveNetworkInfo != null && mActiveNetworkInfo.isConnected();
    }

    public static boolean isWifiConnected(Context mContext) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifiNetworkInfo != null && mWifiNetworkInfo.isConnected();
    }

    public static String getWifiConnectionInfo(Context mContext) {
        WifiManager mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        return mWifiInfo.toString();
    }

    public static String getConnectionType(Context mContext) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mActiveNetworkInfo != null && mActiveNetworkInfo.isConnected()) {
            if (mActiveNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return "Wi-Fi";
            } else if (mActiveNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return "Mobile"; //Móvel
            } else if (mActiveNetworkInfo.getType() == ConnectivityManager.TYPE_VPN) {
                return "VPN";
            } else {
                return "Other"; //Outro
            }
        } else {
            return "Disconnected"; //Desconectado
        }
    }

}
