package com.thinear.pinterest.api;

import android.os.Build;

import com.thinear.pinterest.base.BaseApplication;
import com.thinear.pinterest.base.Device;

public class ApiClientHelper {

    public static String getAgent() {
        String tmp = "Pinterest for Android/%s (%s; %s)";
        if(Device.isTablet()) {
            tmp = "Pinterest for Android Tablet/%s (%s; %s)";
        }
        return String.format(tmp, BaseApplication.appVersion(), Build.DEVICE, Build.VERSION.RELEASE);
    }
}
