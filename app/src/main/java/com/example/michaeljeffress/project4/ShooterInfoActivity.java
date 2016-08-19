package com.example.michaeljeffress.project4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class ShooterInfoActivity extends AppCompatActivity {
    private TextView shooterName;
    private TextView averageScore;
    private ListView recentRoundScoring;
    private ListView recentScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_info);




    }


}
