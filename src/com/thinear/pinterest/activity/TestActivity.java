package com.thinear.pinterest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.thinear.pinterest.api.ApiResponseJsonWrapper;
import com.thinear.pinterest.api.BaseApiResponseHandler;
import com.thinear.pinterest.api.impl.SignApiExecutor;

public class TestActivity extends Activity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            SignApiExecutor.login("wubingxian911@gmail.com", "w1b2x3@1030", new SigninResponseHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SigninResponseHandler extends BaseApiResponseHandler {
        @Override
        public void onFailure(Throwable throwable, ApiResponseJsonWrapper wrapper) {
            Log.d(TAG, "result:" + wrapper.toString());
        }

        @Override
        public void onSuccess(ApiResponseJsonWrapper wrapper) {
            Log.d(TAG, "result:" + wrapper.toString());
        }
    }
}
