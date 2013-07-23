package com.thinear.pinterest.api;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiResponseJsonWrapper {

    public static final int ACCESS_DENIED = 7;
    public static final int ACCOUNT_ALREADY_LINKED = 101;
    public static final int API_DOWN = 13;
    public static final int API_MEHTOD_NOT_FOUND = 11;
    public static final int AUTHENTICATION_FAILED = 2;
    public static final int AUTHENTICATION_LEVEL_INVALID = 4;
    public static final int AUTHORIZATION_FAILED = 3;
    public static final int BOARD_FOLLOW_BLOCKED = 43;
    public static final int BOARD_FOLLOW_BLOCKING = 44;
    public static final int BOARD_FOLLOW_FAILED = 42;
    public static final int BOARD_NOT_FOUND = 40;
    public static final int BOOKMARK_NOT_FOUND = 10;
    public static final int CATEGORY_NOT_FOUND = 41;
    public static final int COMMENT_BLOCKED = 231;
    public static final int COMMENT_BLOCKING = 232;
    public static final int COMMENT_FAILED = 230;
    public static final int COMMENT_NOT_FOUND = 120;
    public static final int DOMAIN_NOT_FOUND = 70;
    public static final int EMAIL_TAKEN = 91;
    public static final int FACEBOOK_LINK_NOT_FOUND = 102;
    public static final int FIND_FRIENDS_TRY_AGAIN = 110;
    public static final int INVALID_ACCESS_TOKEN = 100;
    public static final int INVALID_PARAMS = 1;
    public static final int INVALID_USERNAME = 90;
    public static final int LIKE_BLOCKED = 211;
    public static final int LIKE_BLOCKING = 212;
    public static final int LIKE_FAILED = 210;
    public static final int LIKE_NOT_FOUND = 60;
    public static final int LIMIT_EXCEEDED = 8;
    public static final int LOGIN_BAD_PASSWORD = 80;
    public static final int LOGIN_INVALID_TOKEN = 81;
    public static final int METHOD_NOT_ALLOWED = 5;
    public static final int NOT_AUTHORIZATION = 6;
    public static final int PIN_NOT_FOUND = 50;
    public static final int PRIVATE_BOARD_LIMIT = 45;
    public static final int REPIN_BLOCKED = 201;
    public static final int REPIN_BLOCKING = 202;
    public static final int REPIN_FAILED = 200;
    public static final int SERVER_ERROR = 12;
    public static final int SPAM_CONTENT = 14;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 16;
    public static final int TWITTER_LINK_NOT_FOUND = 103;
    public static final int USERNAME_TAKEN = 92;
    public static final int USER_ALREADY_EXISTS = 31;
    public static final int USER_FOLLOW_BLOCKED = 33;
    public static final int USER_FOLLOW_BLOCKING = 34;
    public static final int USER_FOLLOW_FAILED = 32;
    public static final int USER_NOT_FOUND = 30;
    public static final int VALIDATION_ERROR = 15;
    public static final int WRITE_BANNED = 9;

    public String bookmark;
    public int code;
    public Object data;
    public String generated_at;
    public String message;
    public String message_detail;
    public String nag_message;
    public String nag_theme;
    public String status;

    public ApiResponseJsonWrapper(JSONObject json) {
        if(json == null) {
            return;
        }

        code            = json.optInt("code", -1);
        status          = json.optString("status");
        bookmark        = json.optString("bookmark");
        generated_at    = json.optString("generated_at");
        message         = json.optString("message");
        message_detail  = json.optString("message_detail");
        data            = json.opt("data");

        try {
            JSONObject search_nag = json.optJSONObject("search_nag");
            if(search_nag == null) {
                return;
            }
            JSONObject nag = search_nag.optJSONObject("nag");
            if(nag == null) {
                return;
            }

            String tmp = "";
            JSONArray messages = nag.optJSONArray("messages");
            if(messages != null && messages.length() > 0) {
                for(int i = 0; i < messages.length(); i++) {
                    if(i > 0) {
                        tmp = (new StringBuilder(tmp)).append("<br/><br/>").toString();
                    }
                    tmp = (new StringBuilder(tmp)).append(messages.getString(i)).toString();
                }
            }
            nag_message = tmp;
            nag_theme = nag.getString("theme");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject invalidateResponse(JSONObject json, String message) {
        if(json == null) {
            json = new JSONObject();
        } else {
            try {
                json.put("code", 12);
                json.put("message", message);
                json.put("messageDetail", "");
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        return json;
    }

    public final String getMessageDetailIfExist() {
        if(!TextUtils.isEmpty(message_detail)) {
            return message_detail;
        } else {
            return message;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[code=" + code + "]\n");
        builder.append("[status=" + status + "]\n");
        builder.append("[message=" + message + "]\n");
        builder.append("[data=" + data.toString() + "]\n");
        return builder.toString();
    }
}
