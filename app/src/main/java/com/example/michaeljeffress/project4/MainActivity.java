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

import com.example.michaeljeffress.project4.Data.Game;
import com.example.michaeljeffress.project4.Data.GameDayData;
import com.example.michaeljeffress.project4.Data.Life6;
import com.example.michaeljeffress.project4.Data.PlayerInfo1;
import com.example.michaeljeffress.project4.Data.Players7;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity  {
    private String TAG = "Scoring App";

    private EditText editText;
    private Button addButton;
    private Button shootButton;
    private ListView main_Squad_ListView;
    private Spinner dropdown;
    private ArrayList<Object> squadArrayList = new ArrayList(); // TODO create array of players instead of objects

    private String shooterName;

    private PlayerInfo1 playerInfo1;
    private PlayerInfo1 p1;
    private PlayerInfo1 p2;
    private PlayerInfo1 p3;
    private PlayerInfo1 p4;
    private PlayerInfo1 p5;
    private Game game = new Game(p1, p2, p3, p4, p5);

    private Players7 players;
    private Life6 life;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference shootersRef;
    private DatabaseReference lifeRef, playerRef;
    private FirebaseListAdapter<PlayerInfo1> playersAdapter;
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
        shootersRef = firebaseDatabase.getReference("players/allPlayers");
        playerRef = firebaseDatabase.getReference("players");
        lifeRef = firebaseDatabase.getReference("life");


        getPlayersFromFireBase();

        getShootsFromFirebase();

        squadAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, squadArrayList);
        main_Squad_ListView.setAdapter(squadAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shooterName = editText.getText().toString();
                if (!shooterName.isEmpty()) {
                    squadArrayList.add(shooterName);
                    squadAdapter.notifyDataSetChanged();
                    players.addPlayer(new PlayerInfo1(shooterName));
                    playerRef.setValue(players);
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
                    PlayerInfo1 playerInfo1 = (PlayerInfo1) dropdown.getAdapter().getItem(i);
                    squadArrayList.add(playerInfo1.getShooterName());
                    squadAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, squadArrayList);
                    main_Squad_ListView.setAdapter(squadAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        playersAdapter = new FirebaseListAdapter<PlayerInfo1>(MainActivity.this, PlayerInfo1.class, android.R.layout.simple_expandable_list_item_1, shootersRef){
            @Override
            protected void populateView(View v, PlayerInfo1 model, int position) {
                ((TextView) v).setText(model.getShooterName());
            }
        };
        dropdown.setAdapter(playersAdapter);


        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDayData gameDayData = new GameDayData();
                gameDayData.createShoot(p1,p2,p3,p4,p5); //TODO add arraylist indexs to represent players
                int gameDayIndex = life.getGameDayData().size() - 1;
                int shootIndex = life.getGameDayData().get(gameDayIndex).getShoots().size() - 1;


                life.getGameDayData().get(life.getGameDayData().size() - 1).createShoot(p1,p2,p3,p4,p5);
                lifeRef.setValue(life);

                Intent intent = new Intent(MainActivity.this, ShootingActivity.class);
                intent.putExtra(ShootingActivity.KEY_DAY_INDEX, gameDayIndex);
                intent.putExtra(ShootingActivity.KEY_SHOOT_INDEX, shootIndex);
                startActivity(intent);
            }
        });

//       makingFakeGame();
//        makeFakePlayers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        playersAdapter.cleanup();
    }

    private void getPlayersFromFireBase(){
        players = new Players7();
        playerRef.child("allPlayers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                players = new Players7();
                while(it.hasNext()){
                    PlayerInfo1 pInfo = it.next().getValue(PlayerInfo1.class);
                    players.addPlayer(pInfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getShootsFromFirebase(){
        life = new Life6();
        lifeRef.child("gameDayData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                life = new Life6();
                while(it.hasNext()){
                    GameDayData gameDayData = it.next().getValue(GameDayData.class);
                    life.addGameDay(gameDayData);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//    private void makingFakeGame (){
//
//        PlayerInfo1 playerInfo1 = new PlayerInfo1();
//        PlayerInfo1 playerInfo2 = new PlayerInfo1();
//        PlayerInfo1 playerInfo3 = new PlayerInfo1();
//        PlayerInfo1 playerInfo4 = new PlayerInfo1();
//        PlayerInfo1 playerInfo5 = new PlayerInfo1();
//
//        playerInfo1.setShooterName("Bobv");
//        playerInfo2.setShooterName("Bobv2");
//        playerInfo3.setShooterName("Bobv3");
//        playerInfo4.setShooterName("Bobv4");
//        playerInfo5.setShooterName("Bobv5");
//
//        Life6 life6 = new Life6();
//
//        GameDayData gameDayData = new GameDayData();
//
//        gameDayData.createShoot(playerInfo1,playerInfo2, playerInfo3,playerInfo4,playerInfo5);
//        gameDayData.createShoot(playerInfo1,playerInfo2, playerInfo3,playerInfo4,playerInfo5);
//        gameDayData.createShoot(playerInfo1,playerInfo2, playerInfo3,playerInfo4,playerInfo5);
//
//
//        gameDayData.getShoots().get(0).getShooterOne().getStation1().setS1(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation1().setS2(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation1().setS3(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation1().setS4(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation1().setS5(true);
//
//        gameDayData.getShoots().get(0).getShooterOne().getStation2().setS1(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation2().setS2(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation2().setS3(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation2().setS4(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation2().setS5(true);
//
//        gameDayData.getShoots().get(0).getShooterOne().getStation3().setS1(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation3().setS2(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation3().setS3(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation3().setS4(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation3().setS5(true);
//
//        gameDayData.getShoots().get(0).getShooterOne().getStation4().setS1(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation4().setS2(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation4().setS3(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation4().setS4(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation4().setS5(true);
//
//        gameDayData.getShoots().get(0).getShooterOne().getStation5().setS1(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation5().setS2(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation5().setS3(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation5().setS4(true);
//        gameDayData.getShoots().get(0).getShooterOne().getStation5().setS5(true);
//
//        gameDayData.getShoots().get(0).getShooterTwo().getStation1().setS1(true);
//        gameDayData.getShoots().get(0).getShooterTwo().getStation1().setS2(true);
//        gameDayData.getShoots().get(0).getShooterTwo().getStation1().setS3(true);
//        gameDayData.getShoots().get(0).getShooterTwo().getStation1().setS4(true);
//        gameDayData.getShoots().get(0).getShooterTwo().getStation1().setS5(true);
//
//        life6.addGameDay(gameDayData);
//        life6.addGameDay(gameDayData);
//        life6.addGameDay(gameDayData);
//
//        firebaseDatabase.getReference("life").setValue(life6);
//
//    }
//
//    public void makeFakePlayers(){
//        Players7 players7 = new Players7();
//
//        PlayerInfo1 playerInfo1 = new PlayerInfo1();
//        PlayerInfo1 playerInfo2 = new PlayerInfo1();
//        PlayerInfo1 playerInfo3 = new PlayerInfo1();
//        PlayerInfo1 playerInfo4 = new PlayerInfo1();
//        PlayerInfo1 playerInfo5 = new PlayerInfo1();
//
//        playerInfo1.setShooterName("Bobv");
//        playerInfo2.setShooterName("Bobv2");
//        playerInfo3.setShooterName("Bobv3");
//        playerInfo4.setShooterName("Bobv4");
//        playerInfo5.setShooterName("Bobv5");
//
//        players7.addPlayer(playerInfo1);
//        players7.addPlayer(playerInfo2);
//        players7.addPlayer(playerInfo3);
//        players7.addPlayer(playerInfo4);
//        players7.addPlayer(playerInfo5);
//
//        firebaseDatabase.getReference("players").setValue(players7);

//    }

}
