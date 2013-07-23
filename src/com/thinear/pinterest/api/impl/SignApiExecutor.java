package com.thinear.pinterest.api.impl;

import com.thinear.pinterest.api.BaseApiResponseHandler;

import java.util.Map;
import java.util.TreeMap;

public class SignApiExecutor extends BaseApiExecutor {

    public static void login(String username, String password, BaseApiResponseHandler handler) throws Exception {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("username_or_email", username);
        map.put("password", password);
        request("login/", "POST", map, handler);
    }
}
