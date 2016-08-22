package com.example.michaeljeffress.project4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

    private static final String ARG_STATION_NUMBER = "station_number";
    private static final String ARG_SQUAD_MEMBER = "squad_member";
    private FirebaseListAdapter infoAdapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference scoresRef = firebaseDatabase.getReference("scores");


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
         scoresRef = firebaseDatabase.getReference("scores");

        infoAdapter = new FirebaseListAdapter<String>(getActivity(), String.class, R.layout.list_squad_layout, scoresRef.child(squadKey).child(("squadList"))){
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView1 = (TextView) v.findViewById(R.id.shooter_placeholder);
               textView1.setText(model);
            }
        };
        squadUpdates();
        listView.setAdapter(infoAdapter);
        return rootView;
    }


    private void squadUpdates() {
        scoresRef.addChildEventListener(new ChildEventListener() {
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