package com.thinear.pinterest.base;

public class Device {

    private static Boolean sIsTablet = null;

    public static boolean isTablet() {
        if(sIsTablet == null) {
            if((BaseApplication.getAppContext().getResources().getConfiguration().screenLayout & 15) >= 3) {
                sIsTablet = true;
            } else {
                sIsTablet = false;
            }
        }
        return sIsTablet;
    }
}
