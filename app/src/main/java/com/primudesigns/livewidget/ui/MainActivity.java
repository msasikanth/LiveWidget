package com.primudesigns.livewidget.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.databinding.ActivityMainBinding;
import com.primudesigns.livewidget.ui.fragments.EventsListFragment;
import com.primudesigns.livewidget.ui.fragments.UpcomingFragment;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mainBinding.toolbar);

        //TODO 1 : ADD FAVORITES SECTION IN FUTURE

        mainBinding.bottomBar.setDefaultTab(R.id.navigation_home);
        mainBinding.bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.navigation_home:
                        EventsListFragment eventsListFragment = new EventsListFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.content, eventsListFragment)
                                .commit();
                        break;

                    case R.id.navigation_upcoming:
                        UpcomingFragment upcomingFragment = new UpcomingFragment();
                        FragmentManager upFrag = getSupportFragmentManager();
                        upFrag.beginTransaction()
                                .replace(R.id.content, upcomingFragment)
                                .commit();
                        break;
                }

            }
        });


    }


}
