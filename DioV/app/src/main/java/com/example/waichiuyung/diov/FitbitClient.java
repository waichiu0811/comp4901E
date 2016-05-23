package com.example.waichiuyung.diov;

import android.util.Log;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by samwalker on 3/5/16.
 */
public class FitbitClient {

    private static FitbitClient fitbitClient;

    public static AsyncHttpClient client = new AsyncHttpClient();

    String sleepDataLocal = null;

    private FitbitClient() {
        client.setEnableRedirects(true);
    }

    public static FitbitClient getInstance() {
        if (fitbitClient == null) {
            fitbitClient = new FitbitClient();
        }
        return fitbitClient;
    }

    private static final String BASE_URL = "https://www.fitbit.com/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public void auth(final WebView webView) {
        String authUrl = "oauth2/authorize?" +
                "response_type=token&" +
                "client_id=227LP6&" +
                "redirect_uri=fitbit-void://get-token&" +
                "scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&" +
                "expires_in=60480";

        client.post(getAbsoluteUrl(authUrl), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(statusCode);
                System.out.println(responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                System.out.println(responseString);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadData(responseString, "text/html", "UTF-8");
            }
        });
    }

    public void getUserProfile(final FitbitResponseHandler fitbitResponseHandler) {
        String url = "https://api.fitbit.com/1/user/-/profile.json";
        client.get(url, null, new FitbitJsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                fitbitResponseHandler.onGetUserProfile(response);
            }
        });
    }

    public void getHeartData(final FitbitResponseHandler fitbitResponseHandler) {
        String url = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d.json";
        client.get(url, null, new FitbitJsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                fitbitResponseHandler.onGetHeartData(response);
            }

        });
    }

    public void getSleepData(final FitbitResponseHandler fitbitResponseHandler) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String url = "https://api.fitbit.com/1/user/-/sleep/date/2015-08-23.json";
        String url = "https://api.fitbit.com/1/user/-/sleep/date/"+format.format(new Date())+".json";
        Log.i("url", url);
        client.get(url, null, new FitbitJsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                fitbitResponseHandler.onGetSleepData(response);
            }
        });
    }

    public void setTokenToHeader(String token) {
        client.addHeader("Authorization", "Bearer "+token);
    }

    abstract class FitbitJsonResponseHandler extends JsonHttpResponseHandler {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.e("error", responseString);
        }
    }

    public String getSleepDataLocal() {
        return sleepDataLocal;
    }

    public void setSleepDataLocal(String sleepDataLocal) {
        this.sleepDataLocal = sleepDataLocal;
    }
}
