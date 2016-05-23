package com.example.waichiuyung.diov;


import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.media.MediaPlayer;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, NumberPicker.OnValueChangeListener{
    MediaPlayer mySound;
    boolean doubleBackToExitPressedOnce = false;
    private Handler mHandler;
    private ViewPager viewPager;
    private FrameLayout frame;
    private TextView mDebugText;
    private int exerciseMinute,exerciseSecond;
    private View exerciseView;
    private FitbitClient fitbitClient;

    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init_toolbar();
        init_navigation();

        mySound = MediaPlayer.create(this,R.raw.sleep);
        FloatingActionButton mbtnExercise = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        mbtnExercise.setOnClickListener(this);
        mHandler = new Handler();

        //init exercise frame
        frame = (FrameLayout)findViewById(R.id.exerciseFrame);
        exerciseView = LayoutInflater.from(this).inflate(R.layout.exercise_fragment, null);
        frame.addView(exerciseView,0);
        NumberPicker durationMinute = (NumberPicker)findViewById(R.id.exerciseMinute);
        NumberPicker durationSecond = (NumberPicker)findViewById(R.id.exerciseSecond);
        durationMinute.setMinValue(0);
        durationMinute.setMaxValue(5);
        durationMinute.setValue(3);
        durationMinute.setOnValueChangedListener(this);
        durationSecond.setMinValue(0);
        durationSecond.setMaxValue(59);
        durationSecond.setValue(0);
        durationSecond.setOnValueChangedListener(this);
        exerciseMinute = 3;
        exerciseSecond = 0;
        mDebugText = (TextView)findViewById(R.id.debugText);
        frame.setVisibility(View.INVISIBLE);
        initChart();
    }
    public void playMusic(View view) {
        mySound.start();
    }
    public void stopMusic(View view) {
        mySound.pause();
    }

    public void initChart() {
        String token = getTokenFromIntent(getIntent());
        if (token != null) {
            fitbitClient = FitbitClient.getInstance();
            fitbitClient.setTokenToHeader(token);
            fitbitClient.getHeartData(adapter.getHomeFragment());
            fitbitClient.getSleepData(adapter.getHomeFragment());
            fitbitClient.getUserProfile(adapter.getHomeFragment());
        }
    }


    private void init_toolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));

       // tabLayout.getTabAt(0).setIcon(R.drawable.home_tool); //icon instead of string
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ff3377")); // pink
        tabLayout.addTab(tabLayout.newTab().setText("Sleeping"));
      //  tabLayout.getTabAt(1).setIcon(R.drawable.sleeping_navi);
        tabLayout.addTab(tabLayout.newTab().setText("Pressure"));
      //  tabLayout.getTabAt(2).setIcon(R.drawable.pressure_navi);
        tabLayout.addTab(tabLayout.newTab().setText("Focus"));
      //  tabLayout.getTabAt(3).setIcon(R.drawable.focus_navi);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()); //extra finger exercise fragment
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                frame.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void init_navigation(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (frame.getVisibility() == View.VISIBLE) {
            frame.setVisibility(View.INVISIBLE);
        }else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
            return true;
  //      }

//        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Class fragmentClass;

        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            // Handle the camera action
        } else if (id == R.id.nav_finger) {
            if (frame.getVisibility() == View.VISIBLE){
                frame.setVisibility(View.INVISIBLE);
            }else {
                frame.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.nav_sleeping) {
            viewPager.setCurrentItem(1);
            //fragment=new SleepingFragment();
            //fragmentTransaction.replace(R.id.container,fragment);
            //fragmentTransaction.commit();
            //mySound = MediaPlayer.create(this,R.raw.sleep);

        } else if (id == R.id.nav_pressure) {
            viewPager.setCurrentItem(2);
            //fragment=new PressureFragment();
            //fragmentTransaction.replace(R.id.container,fragment);
            //fragmentTransaction.commit();

        } else if (id == R.id.nav_focus) {
            viewPager.setCurrentItem(3);
            //fragment=new FocusFragment();
            //fragmentTransaction.replace(R.id.container,fragment);
            //fragmentTransaction.commit();

        } else if (id == R.id.nav_setting) {
        } else if (id == R.id.nav_logout) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.floatingActionButton:
                if (frame.getVisibility() == View.VISIBLE){
                    frame.setVisibility(View.INVISIBLE);
                }else {
                    frame.setVisibility(View.VISIBLE);
                    Button mbtnStartExercise = (Button)findViewById(R.id.btnStartExercise);
                    mbtnStartExercise.setOnClickListener(this);
                }
                break;
            case R.id.btnStartExercise:
                Intent i = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                i.putExtra("duration",exerciseMinute*60+exerciseSecond);
                startActivity(i);
                //mUnityPlayer = new UnityPlayer(this);
                //setContentView(mUnityPlayer);
                //mUnityPlayer.requestFocus();
                //initUnity();
                //LayoutParams lp = new LayoutParams (LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                //frame.removeView(exerciseView);
                //frame.addView(mUnityPlayer.getView(), 0);
                //frame.addView(exerciseView,1);
                //mUnityPlayer.resume();
                //unityStart = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()){
            case R.id.exerciseMinute:
                exerciseMinute = newVal;
                mDebugText.setText("duration: " + (exerciseMinute * 60 + exerciseSecond));
                break;
            case R.id.exerciseSecond:
                exerciseSecond = newVal;
                mDebugText.setText("duration: "+ (exerciseMinute*60+exerciseSecond));
                break;

        }
    }
}
