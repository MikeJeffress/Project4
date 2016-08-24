package com.example.michaeljeffress.project4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.michaeljeffress.project4.Data.LaneData;
import com.example.michaeljeffress.project4.Data.PlayerData;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by michaeljeffress on 8/18/16.
 */
public class ShooterFragment extends Fragment {
    private String TAG = "Scoring App";
    private String squadKey;
    private LaneData laneData = new LaneData();
    private int dayIndex;
    private int shootIndex;

    public static final String KEY_DAY_INDEX = "day-index";
    public static final String KEY_SHOOT_INDEX = "shoot-index";

    private static final String ARG_STATION_NUMBER = "station_number";
    private FirebaseListAdapter infoAdapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference lifeRef = firebaseDatabase.getReference("life");

    private DatabaseReference ref;

    public ShooterFragment() {
    }

    public void setSquadKey(String squadKey) {
        this.squadKey = squadKey;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        infoAdapter.cleanup();
    }

    public static ShooterFragment newInstance(int stationNumber) {
        ShooterFragment fragment = new ShooterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATION_NUMBER, stationNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.round_format, getArguments().getInt(ARG_STATION_NUMBER)));
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_listView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        dayIndex = sharedPreferences.getInt(KEY_DAY_INDEX, 0);
        shootIndex = sharedPreferences.getInt(KEY_SHOOT_INDEX, 0);

        ref = lifeRef.child("gameDayData").child(String.valueOf(dayIndex)).child("shoots").child(String.valueOf(shootIndex)).child("shooters");

        infoAdapter = new FirebaseListAdapter<PlayerData>(getActivity(), PlayerData.class, R.layout.list_squad_layout, ref) {



            @Override
            protected void populateView(View v, PlayerData model, final int position) {
                if (model == null || model.getShooterInfo() == null || model.getShooterInfo().getShooterName() == null){
                    v.setVisibility(View.GONE);
                    return;
                }
                TextView textView1 = (TextView) v.findViewById(R.id.shooter_placeholder);
                textView1.setText(model.getShooterInfo().getShooterName());

                TextView positionTextView = (TextView) v.findViewById(R.id.fragment_list_position_number);
                int positionPlus = position + 1;
                positionTextView.setText("  -  Position " + positionPlus);

                System.out.println("position " + position);

                final CheckBox checkBoxFirst = (CheckBox) v.findViewById(R.id.checkbox_FirstTarget);
                final CheckBox checkBoxSecond = (CheckBox) v.findViewById(R.id.checkbox_SecondTarget);
                final CheckBox checkBoxThird = (CheckBox) v.findViewById(R.id.checkbox_ThirdTarget);
                final CheckBox checkBoxFourth = (CheckBox) v.findViewById(R.id.checkbox_ForthTarget);
                final CheckBox checkBoxFifth = (CheckBox) v.findViewById(R.id.checkbox_FifthTarget);

                CheckedListener listenerBox1 = new CheckedListener(model, position, CheckedListener.SHOT_1);
                CheckedListener listenerBox2 = new CheckedListener(model, position, CheckedListener.SHOT_2);
                CheckedListener listenerBox3 = new CheckedListener(model, position, CheckedListener.SHOT_3);
                CheckedListener listenerBox4 = new CheckedListener(model, position, CheckedListener.SHOT_4);
                CheckedListener listenerBox5 = new CheckedListener(model, position, CheckedListener.SHOT_5);

                checkBoxFirst.setOnCheckedChangeListener(listenerBox1);
                checkBoxSecond.setOnCheckedChangeListener(listenerBox2);
                checkBoxThird.setOnCheckedChangeListener(listenerBox3);
                checkBoxFourth.setOnCheckedChangeListener(listenerBox4);
                checkBoxFifth.setOnCheckedChangeListener(listenerBox5);
            }
        };
        squadUpdates();
        listView.setAdapter(infoAdapter);
        return rootView;
    }


    private void squadUpdates() {
        lifeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildAdded: ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildChanged: ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onChildRemoved: ");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildMoved: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: " + databaseError.toException());
            }
        });
    }


    private class CheckedListener implements CompoundButton.OnCheckedChangeListener {
        public static final int SHOT_1 = 1;
        public static final int SHOT_2 = 2;
        public static final int SHOT_3 = 3;
        public static final int SHOT_4 = 4;
        public static final int SHOT_5 = 5;

        private PlayerData model;
        private int position;
        private int shotNumber;

        public CheckedListener(PlayerData model, int position, int shotNumber) {
            this.model = model;
            this.position = position;
            this.shotNumber = shotNumber;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (shotNumber){
                case SHOT_1:
                    model.getStation1().setS1(compoundButton.isChecked());
                    break;
                case SHOT_2:
                    model.getStation1().setS2(compoundButton.isChecked());
                    break;
                case SHOT_3:
                    model.getStation1().setS3(compoundButton.isChecked());
                    break;
                case SHOT_4:
                    model.getStation1().setS4(compoundButton.isChecked());
                    break;
                case SHOT_5:
                    model.getStation1().setS5(compoundButton.isChecked());
                    break;
            }

            switch (position){
                case 0:
                    ref.child("0").setValue(model);
                    break;
                case 1:
                    ref.child("1").setValue(model);
                    break;
                case 2:
                    ref.child("2").setValue(model);
                    break;
                case 3:
                    ref.child("3").setValue(model);
                    break;
                case 4:
                    ref.child("4").setValue(model);
                    break;
            }

        }


    }



}