package com.thinear.pinterest.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

public class BaseApplication extends Application {

    private static String PERSIST_NAME = "pinterest.persist";
    private static Context sAppContext;

    private static String sAppVersion = null;
    private static int sAppVersionCode = -1;

    public static BaseApplication getAppContext() {
        return (BaseApplication)sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        init();
    }

    protected void init() {

    }

    public static String appVersion() {
        if(sAppVersion == null) {
            try {
                PackageManager pm = getAppContext().getPackageManager();
                sAppVersion = pm.getPackageInfo(getAppContext().getPackageName(), 0).versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sAppVersion;
    }

    public static int appVersionCode() {
        if(sAppVersionCode == -1) {
            try {
                PackageManager pm = getAppContext().getPackageManager();
                sAppVersionCode = pm.getPackageInfo(getAppContext().getPackageName(), 0).versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sAppVersionCode;
    }

    /* Default preference begin */
    private static SharedPreferences getDefaultPreferences() {
        return getAppContext().getSharedPreferences(PERSIST_NAME, MODE_PRIVATE);
    }

    public static void saveDefaultPrefString(final String key, final String value) {
        SharedPreferences.Editor editor = getDefaultPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaultPrefString(final String key, final String defValue) {
        return getDefaultPreferences().getString(key, defValue);
    }

    public static void saveDefaultPrefInt(final String key, final int value) {
        SharedPreferences.Editor editor = getDefaultPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getDefaultPrefInt(final String key, final int defValue) {
        return getDefaultPreferences().getInt(key, defValue);
    }

    public static void saveDefaultPrefBoolean(final String key, final boolean value) {
        SharedPreferences.Editor editor = getDefaultPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getDefaultPrefBoolean(final String key, final boolean defValue) {
        return getDefaultPreferences().getBoolean(key, defValue);
    }
    /* Default preference end */
}
