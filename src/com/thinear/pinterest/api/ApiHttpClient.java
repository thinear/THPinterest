package com.thinear.pinterest.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiHttpClient {

    private static String API_URL = "https://api.pinterest.com/v3/%s";
    public static String ASSET_URL = "http://media-cache.pinterest.com/%s";
    public static String ATTRIB_ASSET_URL = "http://passets-ec.pinterest.com/%s";
    private static final String DEFAULT_API_URL = "https://api.pinterest.com/v3/%s";

    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";

    public static AsyncHttpClient sClient = new AsyncHttpClient();

    public static String getApiUrl() {
        return API_URL;
    }

    public static String getAbsoluteApiUrl(String path) {
        return String.format(API_URL, path);
    }

    public static String getAssetUrl(String path) {
        if(path.indexOf("/") == 0) {
            path = path.substring(1);
        }
        if(!path.contains("http")) {
            path = String.format(ASSET_URL, path);
        }
        return path;
    }

    public static String getAttributeAssetUrl(String path) {
        if(path.indexOf("/") == 0) {
            path = path.substring(1);
        }
        return String.format(ATTRIB_ASSET_URL, path);
    }

    public static void addHeader(String key, String value) {
        sClient.addHeader(key, value);
    }

    public static void cancelAll(Context context) {
        sClient.cancelRequests(context, true);
    }

    public static void delete(String path, AsyncHttpResponseHandler handler) {
        sClient.delete(getAbsoluteApiUrl(path), handler);
    }

    public static void setUserAgent(String agent) {
        sClient.setUserAgent(agent);
    }

    public static void get(Context context, String path, AsyncHttpResponseHandler handler) {
        sClient.get(context, getAbsoluteApiUrl(path), handler);
    }

    public static void get(String path, AsyncHttpResponseHandler handler) {
        sClient.get(getAbsoluteApiUrl(path), handler);
    }

    public static void get(String path, RequestParams params, AsyncHttpResponseHandler handler) {
        sClient.get(getAbsoluteApiUrl(path), params, handler);
    }

    public static void getDirect(String url, AsyncHttpResponseHandler handler) {
        sClient.get(url, handler);
    }

    public static void post(Context context, String path, RequestParams params, AsyncHttpResponseHandler handler) {
        sClient.post(context, getAbsoluteApiUrl(path), params, handler);
    }

    public static void post(String path, AsyncHttpResponseHandler handler) {
        sClient.post(getAbsoluteApiUrl(path), handler);
    }

    public static void post(String path, RequestParams params, AsyncHttpResponseHandler handler) {
        sClient.post(getAbsoluteApiUrl(path), params, handler);
    }

    public static void put(String path, AsyncHttpResponseHandler handler) {
        sClient.put(getAbsoluteApiUrl(path), handler);
    }

    public static void put(String path, RequestParams params, AsyncHttpResponseHandler handler) {
        sClient.put(getAbsoluteApiUrl(path), params, handler);
    }

    public static void setApiUrl(final String url) {
        API_URL = url;
    }

}
