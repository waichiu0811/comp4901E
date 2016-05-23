package com.example.waichiuyung.diov;

import org.json.JSONObject;

/**
 * Created by samwalker on 19/5/16.
 */
public interface FitbitResponseHandler {

    void onGetUserProfile(JSONObject json);
    void onGetSleepData(JSONObject json);
    void onGetHeartData(JSONObject json);
    void onError(String errMsg);
}
