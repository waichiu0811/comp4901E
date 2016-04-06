package com.example.waichiuyung.diov;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class SleepingFragment extends Fragment {
MediaPlayer mySound;
    ImageButton img;
    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.sleeping_fragment, container, false);

      //  img= (ImageButton) rootview.findViewById(R.id.imageButton2);
//mySound = MediaPlayer.create(this,R.raw.sleep);

        return rootview;


    }
    public void playMusic(View view) {

       //mySound = MediaPlayer.create(this,R.raw.sleep);
        mySound.start();
    }
}
