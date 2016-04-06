package com.example.waichiuyung.diov;


import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.media.MediaPlayer;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MediaPlayer mySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init_toolbar();
        init_navigation();


        mySound = MediaPlayer.create(this,R.raw.sleep);

    }
    public void playMusic(View view) {
        mySound.start();
    }
    public void stopMusic(View view) {
        mySound.pause();
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

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new com.example.waichiuyung.diov.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
        } else {
            super.onBackPressed();
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

        } else if (id == R.id.nav_sleeping) {
            fragment=new SleepingFragment();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
            mySound = MediaPlayer.create(this,R.raw.sleep);

        } else if (id == R.id.nav_pressure) {
            fragment=new PressureFragment();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_focus) {
            fragment=new FocusFragment();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
