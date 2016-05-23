package com.example.waichiuyung.diov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class BaseActivity extends AppCompatActivity {
    final String PREF = "default";
    protected Object save(String key, Object val) {
        return  val;
    }

    protected String getTokenFromIntent(Intent intent) {
        String token = null;
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                Log.i("uri", uri.toString());
                Pattern pattern = Pattern.compile("(?<=&access_token=).*");
                Matcher matcher = pattern.matcher(uri.toString());
                if (matcher.find()) {
                    token = matcher.group();
                    Log.i("token", token);
                } else {
                    Log.e("token", "not found");
                }
            }
        }
        return token;
    }
}
