package com.example.waichiuyung.diov;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static com.example.waichiuyung.diov.R.raw.sleep;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class SleepingFragment extends Fragment implements  View.OnClickListener{
MediaPlayer mySound1;
    MediaPlayer mySound2;
    MediaPlayer mySound3;
    ImageButton img1;
    ImageButton img2;
    ImageButton img3;
    ImageButton pause1;
    ImageButton pause2;
    ImageButton pause3;

    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.sleeping_fragment, container, false);

        img1= (ImageButton) rootview.findViewById(R.id.imageButton1);
        img1.setOnClickListener(this);

        img2= (ImageButton) rootview.findViewById(R.id.imageButton2);
        img2.setOnClickListener(this);

        img3= (ImageButton) rootview.findViewById(R.id.imageButton3);
        img3.setOnClickListener(this);

        pause1= (ImageButton) rootview.findViewById(R.id.pause1);
        pause1.setOnClickListener(this);

        pause2= (ImageButton) rootview.findViewById(R.id.pause2);
        pause2.setOnClickListener(this);

        pause3= (ImageButton) rootview.findViewById(R.id.pause3);
        pause3.setOnClickListener(this);

        mySound1 = MediaPlayer.create(getActivity() ,R.raw.relax_music1); //the getActivity gives the Media Player the context it needs
        mySound2 = MediaPlayer.create(getActivity() ,R.raw.relax_music2);
        mySound3 = MediaPlayer.create(getActivity() ,R.raw.relax_music3);

        return rootview;


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imageButton1:
                mySound1.start();
                break;

            case R.id.imageButton2:
                mySound2.start();
                break;

            case R.id.imageButton3:
                mySound3.start();
                break;

            case R.id.pause1:
                mySound1.pause();;
                break;

            case R.id.pause2:
                mySound2.pause();;
                break;

            case R.id.pause3:
                mySound3.pause();;
                break;

            default:
                break;
        }
    }
}

