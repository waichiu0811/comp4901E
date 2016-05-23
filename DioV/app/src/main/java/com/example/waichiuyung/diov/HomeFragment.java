package com.example.waichiuyung.diov;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class HomeFragment extends Fragment {


    ImageView imgConnFitbit;
    TextView txtHeart;
    TextView txtWeight;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }
}