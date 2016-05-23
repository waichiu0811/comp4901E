package com.example.waichiuyung.diov;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class FitbitAuthActivity extends BaseActivity {

    WebView wvFitBitAuth;
    FitbitClient fitbitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit_auth);
        fitbitClient = FitbitClient.getInstance();
        wvFitBitAuth = (WebView) findViewById(R.id.wvFitbitAuth);
        fitbitClient.auth(wvFitBitAuth);
    }
}
