package com.example.waichiuyung.diov;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class BaseActivity extends AppCompatActivity {
    final String PREF = "default";
    protected Object save(String key, Object val) {
        return  val;
    }
}
