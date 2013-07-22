package com.thinear.pinterest.api;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class BaseApiResponseHandler extends JsonHttpResponseHandler {

    public String base_url;
    public String method;

    public void onFailure(Throwable throwable, ApiResponseJsonWrapper wrapper) {

    }

    @Override
    public void onFailure(Throwable throwable, String result) {
        try {
            result = String.format("{\"data\":\"%s\"}", result);
            onFailure(throwable, new ApiResponseJsonWrapper(new JSONObject(result)));
        } catch (Exception e) {
            onFailure(throwable, new ApiResponseJsonWrapper(null));
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable throwable, JSONArray array) {
        try {
            String result = String.format("{\"data\":\"%s\"}", array.toString());
            onFailure(throwable, new ApiResponseJsonWrapper(new JSONObject(result)));
        } catch (Exception e) {
            onFailure(throwable, new ApiResponseJsonWrapper(null));
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable throwable, JSONObject json) {
        onFailure(throwable, new ApiResponseJsonWrapper(json));
    }

    public void onSuccess(ApiResponseJsonWrapper wrapper) {

    }

    @Override
    public void onSuccess(JSONObject json) {
        onSuccess(new ApiResponseJsonWrapper(json));
    }

}
