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
import android.widget.Toast;

import com.example.michaeljeffress.project4.Data.ShooterData;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private EditText editText;
    private Button addButton;
    private Button shootButton;
    private ListView main_Squad_ListView;
    private ArrayList<String> main_ArrayListShooterInfo = new ArrayList<>();
    private ArrayList<ShooterData> main_Squad = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference chatRef;
    private FirebaseListAdapter<String> messageAdapter;

    private static int LISTVIEW_REQUEST_CODE  = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.main_editText);
        addButton = (Button) findViewById(R.id.main_buttonAdd);
        shootButton = (Button) findViewById(R.id.main_shootButton);

        main_Squad_ListView = (ListView) findViewById(R.id.main_listView);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, main_ArrayListShooterInfo);
        main_Squad_ListView.setAdapter(arrayAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference("chat-room");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shooterName = editText.getText().toString();
                if (!shooterName.isEmpty()) {
                    //String shooterString = editText.getText().toString();
                    main_ArrayListShooterInfo.add(shooterName);
                    ShooterData shooterInfo = new ShooterData();
                    shooterInfo.setShooter(shooterName);
                    main_Squad.add(shooterInfo);
                    arrayAdapter.notifyDataSetChanged();
                    chatRef.push().setValue(shooterInfo);
                    editText.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Please enter shooter name.", Toast.LENGTH_LONG).show();
                }
            }
        });

        main_Squad_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShooterData shooter = main_Squad.get(position);
                Intent intentMain = new Intent(MainActivity.this, ShooterInfoActivity.class);
                startActivityForResult(intentMain, LISTVIEW_REQUEST_CODE);
            }
        });

        main_Squad_ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                main_Squad.remove(position);
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });

        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShootingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        messageAdapter.cleanup();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LISTVIEW_REQUEST_CODE){
            if(resultCode == RESULT_OK) {
                ArrayList<String> receivedResults = data.getStringArrayListExtra("shooters");
                int position = data.getIntExtra("pos", -1);
                ShooterData shooter = new ShooterData();
                String name = data.getStringExtra("name");
                shooter.setShooter(name);
                shooter.setSquadList(receivedResults);
                main_Squad.set(position, shooter);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
