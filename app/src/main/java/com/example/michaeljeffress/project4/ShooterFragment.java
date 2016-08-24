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
import com.example.michaeljeffress.project4.Data.PlayerInfo;
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
    private Boolean scoreBoolean;
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

        ref = lifeRef.child("gameDayData").child(String.valueOf(dayIndex)).child("shoots").child(String.valueOf(shootIndex));

        infoAdapter = new FirebaseListAdapter<PlayerData>(getActivity(), PlayerData.class, R.layout.list_squad_layout, ref) {

            @Override
            protected void populateView(View v, final PlayerData model, final int position) {
                if (model == null || model.getShooterInfo() == null || model.getShooterInfo().getShooterName() == null){
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

                checkBoxFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        PlayerInfo plaerInfo = new PlayerInfo(model.getShooterInfo().getShooterName());
                        plaerInfo.setShooterID(model.getShooterInfo().getShooterID());
                        PlayerData player = new PlayerData(plaerInfo);

                        player.getStation1().setS1(compoundButton.isChecked());

                        switch (position){
                            case 0:
                                ref.child("shooterOne").setValue(player);
                                break;
                            case 1:
                                ref.child("shooterTwo").setValue(player);
                                break;
                            case 2:
                                ref.child("shooterThree").setValue(player);
                                break;
                            case 3:
                                ref.child("shooterFour").setValue(player);
                                break;
                            case 4:
                                ref.child("shooterFive").setValue(player);
                                break;
                        }
                    }
                });


                checkBoxSecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        model.getStation1().setS2(compoundButton.isChecked());

                    }
                });

                checkBoxThird.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        model.getStation1().setS3(compoundButton.isChecked());

                    }
                });

                checkBoxFourth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        model.getStation1().setS1(compoundButton.isChecked());
                    }
                });

                checkBoxFifth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (checkBoxFirst.isChecked()) {
                            laneData.setS5(b);
                        }
                    }
                });

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

}