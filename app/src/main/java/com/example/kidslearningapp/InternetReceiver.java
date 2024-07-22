package com.example.kidslearningapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                Toast.makeText(context, "WiFi Disabled", Toast.LENGTH_SHORT).show();
            } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                Toast.makeText(context, "WiFi Enabled", Toast.LENGTH_SHORT).show();
            }
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            String status = CheckInternet.getNetworkInfo(context);
            if (status.equals("connected")) {
                Toast.makeText(context.getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
            } else if (status.equals("disconnected")) {
                Toast.makeText(context.getApplicationContext(), "Internet Not Connected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
