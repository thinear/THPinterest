package com.thinear.pinterest.api.impl;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.thinear.pinterest.api.ApiExecutor;
import com.thinear.pinterest.api.ApiHttpClient;
import com.thinear.pinterest.api.BaseApiResponseHandler;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * com.pinterest.api.a.g
 * @author bxwu
 */
public class BaseApiExecutor {
    private static final String TAG = "BaseApiExecutor";

    private static String appendAccessToken(final String path) {
        if(path == null) {
            return null;
        }

        return String.format("%s%saccess_token=%s", path, (path.contains("?") ? "&" : "?"), ApiExecutor.sAccessToken);
    }

    private static String formatParamsValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("{", "%7B").replace("}", "%7D").replace("%7E", "~");
    }

    private static String appendOauth(String method, String url, Map<String, String> params) {
        String host = null;
        try {
            host = URLEncoder.encode(url.split("\\?")[0], "UTF-8");
        } catch (Exception e) {
            host = url;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(method).append("&");
        builder.append(host).append("&");

        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object obj = params.get(key);
            if(!(obj instanceof List)) {
                builder.append(key).append("=");
                builder.append(obj.toString()).append("&");
            } else {
                try {
                    Iterator<Object> iterator2 = ((List)obj).iterator();
                    while (iterator2.hasNext()) {
                        builder.append(key).append("=");
                        builder.append(formatParamsValue(iterator2.next().toString())).append("&");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        String oauth = "";

        String result = builder.toString();
        result = result.substring(0, result.length() - 1);
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(ApiExecutor.sClientSecret.getBytes("UTF-8"), "HMACSHA256");
            Mac mac = Mac.getInstance("HMACSHA256");
            mac.init(secretKeySpec);
            byte result_bytes[] = result.getBytes("UTF-8");
            char hex[] = Hex.encodeHex(mac.doFinal(result_bytes));
            oauth = (new String(hex)).replace(" ", "").replace("<", "").replace(">", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format("%s&oauth_signature=%s", url, oauth);
    }

    protected static void get(String path, RequestParams params, BaseApiResponseHandler handler) {
        fillHandler("GET", path, handler);
        ApiHttpClient.get(appendAccessToken(path), params, handler);
        debugLogtrace(appendAccessToken(path));
    }

    protected static void put(String path, String param, RequestParams params, BaseApiResponseHandler handler) {
        path = String.format(path, param);
        fillHandler("PUT", path, handler);
        ApiHttpClient.put(appendAccessToken(path), params, handler);
        debugLogtrace(appendAccessToken(path));
    }

    protected static void request(String path, String method, Map<String, String> params, BaseApiResponseHandler handler) throws Exception {
        if(ApiExecutor.sClientId == null || ApiExecutor.sClientSecret == null) {
            throw new Exception("CLIENT_ID and CLIENT_SECRET cannot be null! Try calling init(clientId, clientSecret)");
        }
        String url = ApiHttpClient.getAbsoluteApiUrl(path);
        String timestamp = (System.currentTimeMillis()/1000L) + "";
        String get_url = String.format("%s?client_id=%s&timestamp=%s", url, ApiExecutor.sClientId, timestamp);

        RequestParams requestParams = null;
        if(params != null) {
            requestParams = new RequestParams(params);
        } else {
            params = new TreeMap<String, String>();
        }
        params.put("timestamp", timestamp);
        params.put("client_id", ApiExecutor.sClientId);

        String oauthed_url = appendOauth(method, get_url, params);
        debugLogtrace(oauthed_url);

        if(method.equals("POST")) {
            ApiHttpClient.sClient.post(oauthed_url, requestParams, handler);
        } else if(method.equals("PUT")) {
            ApiHttpClient.sClient.put(oauthed_url, requestParams, handler);
        } else if(method.equals("GET")) {
            ApiHttpClient.sClient.get(oauthed_url, requestParams, handler);
        }
    }

    protected static void put(String path) {
        fillHandler("PUT", path, null);
        ApiHttpClient.put(appendAccessToken(path), null);
    }

    protected static void put(String path, RequestParams params, BaseApiResponseHandler handler) {
        fillHandler("PUT", path, handler);
        ApiHttpClient.put(appendAccessToken(path), params, handler);
    }

    protected static void post(String path, String param, RequestParams params, BaseApiResponseHandler handler) {
        path = String.format(path, param);
        fillHandler("POST", path, handler);
        ApiHttpClient.post(appendAccessToken(path), params, handler);
        debugLogtrace(appendAccessToken(path));
    }

    protected static void put(String path, String param, BaseApiResponseHandler handler) {
        path = String.format(path, param);
        fillHandler("PUT", path, handler);
        ApiHttpClient.put(appendAccessToken(path), handler);
    }

    protected static void post(String path, RequestParams params, BaseApiResponseHandler handler) {
        fillHandler("POST", path, handler);
        ApiHttpClient.post(appendAccessToken(path), params, handler);
        debugLogtrace(appendAccessToken(path));
    }

    protected static void delete(String path, String param, BaseApiResponseHandler handler) {
        path = String.format(path, param);
        fillHandler("DELETE", path, handler);
        ApiHttpClient.delete(appendAccessToken(path), handler);
    }

    private static void fillHandler(String method, String path, BaseApiResponseHandler handler) {
        if(ApiExecutor.sAccessToken == null) {
            // TODO
        }
        if(handler != null) {
            handler.method = method;
            handler.base_url = path;
        }
    }

    private static void debugLogtrace(String url) {
        if(ApiExecutor.sDebug) {
            Log.i(TAG, url);
        }
    }

    protected static void get(String path, BaseApiResponseHandler handler) {
        fillHandler("GET", path, handler);
        ApiHttpClient.get(appendAccessToken(path), handler);
        debugLogtrace(appendAccessToken(path));
    }

    protected static void post(String path, String param, BaseApiResponseHandler handler) {
        path = String.format(path, param);
        fillHandler("POST", path, handler);
        ApiHttpClient.post(appendAccessToken(path), handler);
        debugLogtrace(appendAccessToken(path));
    }

    protected static void delete(String path, BaseApiResponseHandler handler) {
        fillHandler("DELETE", path, handler);
        ApiHttpClient.delete(appendAccessToken(path), handler);
    }
}
