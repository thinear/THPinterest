package com.thinear.pinterest.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.thinear.pinterest.R;
import com.thinear.pinterest.api.ApiClientHelper;
import com.thinear.pinterest.api.ApiExecutor;
import com.thinear.pinterest.api.ApiHttpClient;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

public class PinterestApplication extends BaseApplication {

    public static boolean DEBUG = true;

    @Override
    protected void init() {
        super.init();
        setInstallId();

        ApiHttpClient.setUserAgent(ApiClientHelper.getAgent());
        ApiHttpClient.addHeader("Accept-Language", Locale.getDefault().getLanguage());
        ApiHttpClient.addHeader("X-Pinterest-InstallId", getInstallId());

        ApiExecutor.setClientInfo(getClientId(), getClientSecret(getApplicationContext()));
        // TODO
        ApiExecutor.setAccessToken("");// myuser.getAccessToken()
        ApiExecutor.setDebug(DEBUG);
    }

    public static final String getClientId() {
        return ApiExecutor.sClientId;
    }

    public static final String getClientSecret(Context context) {
        String secret = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.global_bg_black, options);

        if(bitmap != null) {
            char buf[] = new char[56];
            for(int i = 0; i < 56; i++) {
                buf[i] = (char)(bitmap.getPixel(0, i) & 0xFF);
            }

            StringBuilder builder = new StringBuilder();
            builder.append(buf);
            byte bytes[] = Base64.decode(builder.toString(), Base64.DEFAULT);
            secret = (new StringBuilder(new String(bytes))).reverse().toString();
        }

        return secret;
    }

    public static String getInstallId() {
        return getDefaultPrefString("pref_install_id", "");
    }

    public static void setInstallId() {
        if(!TextUtils.isEmpty(getInstallId())) {
            return;
        }

        try {
            final String uuid = UUID.randomUUID().toString().toLowerCase().replaceAll("-", "").substring(0, 26);
            String u_uuid = new StringBuilder(uuid).append("user").toString();

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte bytes[] = u_uuid.getBytes("UTF-8");
            char md5[] = Hex.encodeHex(messageDigest.digest(bytes));
            String md5Str = new String(md5);

            StringBuilder builder = new StringBuilder(uuid);
            String installId = builder.append(md5Str.substring(27)).toString();

            saveDefaultPrefString("pref_install_id", installId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
