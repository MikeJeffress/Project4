package com.example.michaeljeffress.project4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaeljeffress.project4.Data.FriendsData;
import com.example.michaeljeffress.project4.Data.ScoreData;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private String TAG = "Scoring App";

    private EditText editText;
    private Button addButton;
    private Button shootButton;
    private ListView main_Squad_ListView;
    private Spinner dropdown;
    private ArrayList<String> squadArrayList = new ArrayList();

    private String shooterName;
    private FriendsData friendsData = new FriendsData();
    private ScoreData scoreData = new ScoreData();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference friendsRef;
    private DatabaseReference scoresRef;
    private FirebaseListAdapter<FriendsData> friendsAdapter;
    private ArrayAdapter squadAdapter;
    private boolean selected = false;


    private static int LISTVIEW_REQUEST_CODE  = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.main_editText);
        addButton = (Button) findViewById(R.id.main_buttonAdd);
        shootButton = (Button) findViewById(R.id.main_shootButton);
        dropdown = (Spinner)findViewById(R.id.main_spinner);
        main_Squad_ListView = (ListView) findViewById(R.id.main_listView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        friendsRef = firebaseDatabase.getReference("friends");
        scoresRef = firebaseDatabase.getReference("scores");



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shooterName = editText.getText().toString();
                if (!shooterName.isEmpty()) {
                    squadArrayList.add(shooterName);
                    squadAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, squadArrayList);
                    main_Squad_ListView.setAdapter(squadAdapter);
                    squadAdapter.notifyDataSetChanged();
                    friendsData.setShooterName(shooterName);
                    friendsRef.push().setValue(friendsData);
                    editText.setText("");

                }
                else {
                    Toast.makeText(MainActivity.this, "Please enter shooter name.", Toast.LENGTH_LONG).show();
                }
            }
        });



        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!selected){
                    selected = true;
                }
                else {
                    FriendsData friendsData = (FriendsData) dropdown.getAdapter().getItem(i);
                    squadArrayList.add(friendsData.getShooterName());
                    squadAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, squadArrayList);
                    main_Squad_ListView.setAdapter(squadAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        friendsAdapter = new FirebaseListAdapter<FriendsData>(MainActivity.this, FriendsData.class, android.R.layout.simple_expandable_list_item_1, friendsRef){
            @Override
            protected void populateView(View v, FriendsData model, int position) {
                ((TextView) v).setText(model.getShooterName());
            }
        };
        dropdown.setAdapter(friendsAdapter);




//        main_Squad_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intentMain = new Intent(MainActivity.this, ShooterInfoActivity.class);
//                startActivityForResult(intentMain, LISTVIEW_REQUEST_CODE);
//            }
//        });
//
//        main_Squad_ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                squadArrayList.remove(position);
//                squadAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });

        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShootingActivity.class);
                scoreData.setSquadList(squadArrayList);
                //create a new database reference set it to scoresRef.push
                DatabaseReference dbRef = scoresRef.push();
                dbRef.setValue(scoreData);
                intent.putExtra("squad_key", dbRef.getKey());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        friendsAdapter.cleanup();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LISTVIEW_REQUEST_CODE){
            if(resultCode == RESULT_OK) {
                ArrayList<String> receivedResults = data.getStringArrayListExtra("shooters");
                int position = data.getIntExtra("pos", -1);
                ScoreData shooter = new ScoreData();
                String name = data.getStringExtra("name");
                //shooter.setShooterName(name);
                shooter.setSquadList(receivedResults);
                //main_Squad.set(position, shooter);
            }
        }
    }


}
