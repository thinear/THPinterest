package com.thinear.pinterest.api;

import com.thinear.pinterest.api.impl.BaseApiExecutor;

public class ApiExecutor extends BaseApiExecutor {

    public static String sClientId = "1431602";
    public static String sDefaultClientId = "1431602";
    public static String sClientSecret = "";
    public static String sAccessToken = null;
    public static boolean sDebug = false;

    public static String sJoinUrl = "http://pinterest.com/join/app/discover/?sso=1&client_id=";

    public static void getAndroidClientApiStatus(BaseApiResponseHandler handler) {
        ApiHttpClient.getDirect("http://passets-cdn.pinterest.com/status/androidstatus.json", handler);
    }

    public static void setClientInfo(final String clientId, final String clientSecret) {
        sClientId = clientId;
        sClientSecret = clientSecret;
    }

    public static void setAccessToken(final String accessToken) {
        sAccessToken = accessToken;
    }

    public static void setDebug(final boolean debug) {
        sDebug = debug;
    }

    public static boolean isDebug() {
        return sDebug;
    }
}
