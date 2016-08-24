package com.example.michaeljeffress.project4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ShootingActivity extends AppCompatActivity {
    public static final String KEY_DAY_INDEX = "day-index";
    public static final String KEY_SHOOT_INDEX = "shoot-index";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String TAG = "Scoring App";


    int dayIndex;
    int shootIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting);

        Intent recievedIntent = getIntent();
        String squad_key = recievedIntent.getStringExtra("squad_key");

        dayIndex = getIntent().getIntExtra(KEY_DAY_INDEX, 0);
        shootIndex = getIntent().getIntExtra(KEY_SHOOT_INDEX, 0);
        String dayIndexString = String.valueOf(dayIndex);
        String shootIndexString = String.valueOf(shootIndex);


        // TOOD: Pass both indeces to fragment in viewPager
        //shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DAY_INDEX, dayIndexString);
        editor.putString(KEY_SHOOT_INDEX, shootIndexString);
        editor.commit();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter.setSquadKey(squad_key);
        mViewPager.setAdapter(mSectionsPagerAdapter);

//        if (squadArrayList == null) {
//            squadArrayList = new ArrayList<String>();
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String squadKey;


        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            ShooterFragment shooterFragment = ShooterFragment.newInstance(position + 1);
            shooterFragment.setSquadKey(squadKey);
            return shooterFragment;

        }



        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return " - ROUND 1";
                case 1:
                    return " - ROUND 2";
                case 2:
                    return " - ROUND 3";
                case 3:
                    return " - ROUND 4";
                case 4:
                    return " - ROUND 5";
            }
            return null;
        }

        public void setSquadKey(String squadKey) {
            this.squadKey = squadKey;
        }
    }


}