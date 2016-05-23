package com.example.waichiuyung.diov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class HomeFragment extends Fragment implements FitbitResponseHandler {


    ImageView imgConnFitbit;
    TextView txtHeart;
    TextView txtWeight;

    BarChart bcSleepData;
    BarData barData;

    JSONArray jsonArray = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        imgConnFitbit = (ImageView)v.findViewById(R.id.imgConnFitbit);
        txtHeart = (TextView) v.findViewById(R.id.txtHeart);
        txtWeight = (TextView) v.findViewById(R.id.txtWeight);
        bcSleepData = (BarChart) v.findViewById(R.id.bcSleepData);


        YAxis yAxis = bcSleepData.getAxisLeft();
        yAxis.setAxisMinValue(0);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawZeroLine(true);

        XAxis xAxis = bcSleepData.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        bcSleepData.setDescription("");
        bcSleepData.setMaxVisibleValueCount(0);

        bcSleepData.setDragEnabled(true);
        barData = new BarData();
        bcSleepData.animateY(2000, Easing.EasingOption.EaseInBack);

        SharedPreferences sh = getActivity().getSharedPreferences("default", 0);
        String json = null;
        if ((json = sh.getString("sleep", null)) != null) {
            try {
                jsonArray = new JSONArray(json);
                refreshDataSet(jsonArray);
                refreshChart();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if ((sh.getString("weight", null)) != null) {
            txtWeight.setText( sh.getString("weight", null) + " kg");
        }

        if ((sh.getString("heart", null)) != null) {
            txtHeart.setText( sh.getString("heart", null) + " bpm");
        }

        imgConnFitbit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FitbitAuthActivity.class));
            }
        });

        return v;
    }

    @Override
    public void onGetUserProfile(JSONObject json) {
        Log.i("fitbit", json.toString());
        String weight = json.optJSONObject("user").optString("weight");
        SharedPreferences sh = getActivity().getSharedPreferences("default", 0);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("weight", weight);
        editor.commit();
        txtWeight.setText(weight + " kg");
    }

    @Override
    public void onGetSleepData(JSONObject json) {
        Log.i("fitbit", json.toString());
        if (json.optJSONArray("sleep") != null && json.optJSONArray("sleep").optJSONObject(0) != null) {
            JSONArray minutesData= json.optJSONArray("sleep").optJSONObject(0).optJSONArray("minuteData");
            if (minutesData != null && minutesData.length() > 0) {
                SharedPreferences sh = getActivity().getSharedPreferences("default", 0);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString("sleep", minutesData.toString());
                editor.commit();
                refreshDataSet(minutesData);
                refreshChart();
            }
        }
    }

    @Override
    public void onGetHeartData(JSONObject json) {
        Log.i("fitbit", json.toString());
        int heartRate = json.optJSONArray("activities-heart").optJSONObject(0).optInt("restingHeartRate", 0);
        if (heartRate != 0) {
            //Do something
            SharedPreferences sh = getActivity().getSharedPreferences("default", 0);
            SharedPreferences.Editor editor = sh.edit();
            editor.putString("weight", String.valueOf(heartRate));
            editor.commit();
            txtHeart.setText(String.valueOf(heartRate)+" bpm");
        }
    }

    @Override
    public void onError(String errMsg) {
        Log.e("error", errMsg);
    }

    public void refreshDataSet(JSONArray minutesData) {
        Log.i("minuteData", minutesData.toString());
        ArrayList<BarEntry> list1 = new ArrayList<>();
        ArrayList<BarEntry> list2 = new ArrayList<>();
        ArrayList<BarEntry> list3 = new ArrayList<>();
//        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<ArrayList<JSONObject>> partialList = new ArrayList<>();
        for (int i = 0; i < (minutesData.length()/60) ; i++) {
            ArrayList<JSONObject> partial = new ArrayList<>();
            for (int j = i*60; j < i*60+60; j++) {
                partial.add(minutesData.optJSONObject(j));
            }
            partialList.add(partial);
        }
        int asleep = 0;
        int awake = 0;
        int reallyAwake = 0;

        int index = 0;

        for (ArrayList<JSONObject> array : partialList) {
            //for every hours
            asleep = 0;
            awake = 0;
            reallyAwake = 0;

            for (JSONObject json: array) {
                if ("1".equals(json.optString("value"))) {
                    asleep += 1;
                }

                if ("2".equals(json.optString("value"))) {
                    awake += 1;
                }

                if ("3".equals(json.optString("value"))) {
                    reallyAwake += 1;
                }
            }

            int max = Math.max(asleep, Math.max(awake, reallyAwake));

            if (max == asleep) {
                list2.add(new BarEntry((float) max, index));
            } else if (max == awake) {
                list3.add(new BarEntry((float) max, index));
            } else if (max == reallyAwake) {
                list1.add(new BarEntry((float) max, index));
            }

            barData.addXValue(String.valueOf(index++));

        }

        BarDataSet dataReallyAwake = new BarDataSet(list1, "Really Awake");
        BarDataSet dataAsSleep = new BarDataSet(list2, "As Slepp");
        BarDataSet dataAwake = new BarDataSet(list3, "Awake");

        dataReallyAwake.setColors(new int[] {R.color.colorAccent}, getActivity());
        dataAsSleep.setColors(new int[] {R.color.colorPrimaryDark}, getActivity());
        dataAwake.setColors(new int[]{R.color.lightBlue}, getActivity());
        barData.clearValues();
        barData.addDataSet(dataReallyAwake);
        barData.addDataSet(dataAsSleep);
        barData.addDataSet(dataAwake);
        barData.notifyDataChanged();
    }

    public void refreshChart() {
        bcSleepData.setData(barData);
        bcSleepData.notifyDataSetChanged();
        bcSleepData.invalidate();
    }
}