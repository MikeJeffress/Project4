package com.example.michaeljeffress.project4.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaeljeffress.project4.Adapters.CustomSquadListAdapter;
import com.example.michaeljeffress.project4.Adapters.NothingSelectedSpinnerAdapter;
import com.example.michaeljeffress.project4.Data.GameDayData;
import com.example.michaeljeffress.project4.Data.Life;
import com.example.michaeljeffress.project4.Data.PlayerInfo;
import com.example.michaeljeffress.project4.Data.Players;
import com.example.michaeljeffress.project4.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements CustomSquadListAdapter.InfoButtonClickListener, CustomSquadListAdapter.DeleteButtonClickListener {
    private String TAG = "Scoring App";

    private EditText editText;
    private Button addButton;
    private Button shootButton;
    private ListView main_Squad_ListView;
    private Spinner dropdown;
    private ArrayList<String> squadArrayList = new ArrayList();

    private String shooterName;

    /* Player Info initialization */
    private PlayerInfo p1;
    private PlayerInfo p2;
    private PlayerInfo p3;
    private PlayerInfo p4;
    private PlayerInfo p5;

    private Players players;
    private Life life;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference shootersRef;
    private DatabaseReference lifeRef, playerRef;
    private FirebaseListAdapter<PlayerInfo> playersAdapter;
    private CustomSquadListAdapter customSquadListAdapter;
    private boolean selected = false;

    private int shooterPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
        setUpFirebase();
        getPlayersFromFireBase();
        getShootsFromFirebase();

        customSquadListAdapter = new CustomSquadListAdapter(this, R.layout.list_squad_layout, squadArrayList);
        customSquadListAdapter.setInfoButtonClickListener(this);
        customSquadListAdapter.setDeleteButtonListener(this);
        main_Squad_ListView.setAdapter(customSquadListAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shooterPosition > 4) {
                    Toast.makeText(MainActivity.this, "Too many shooters added.", Toast.LENGTH_LONG).show();
                    return;
                }
                shooterName = editText.getText().toString();
                if (shooterName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter shooter name.", Toast.LENGTH_LONG).show();
                    return;
                }

                PlayerInfo playerInfo = new PlayerInfo(shooterName);
                players.addPlayer(playerInfo);
                playerRef.setValue(players);
                editText.setText("");

                switch (shooterPosition) {
                    case 0:
                        p1 = playerInfo;
                        break;
                    case 1:
                        p2 = playerInfo;
                        break;
                    case 2:
                        p3 = playerInfo;
                        break;
                    case 3:
                        p4 = playerInfo;
                        break;
                    case 4:
                        p5 = playerInfo;
                        break;
                }
                shooterPosition = shooterPosition + 1;
                squadArrayList.add(playerInfo.getShooterName());
                customSquadListAdapter = new CustomSquadListAdapter(MainActivity.this, R.layout.list_squad_layout, squadArrayList);
                customSquadListAdapter.setInfoButtonClickListener(MainActivity.this);
                customSquadListAdapter.setDeleteButtonListener(MainActivity.this);
                main_Squad_ListView.setAdapter(customSquadListAdapter);
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (shooterPosition > 4) {
                    Toast.makeText(MainActivity.this, "Too many shooters added.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!selected) {
                    selected = true;
                } else {
                    PlayerInfo playerInfo = (PlayerInfo) dropdown.getAdapter().getItem(i);
                    switch (shooterPosition) {
                        case 0:
                            p1 = playerInfo;
                            break;
                        case 1:
                            p2 = playerInfo;
                            break;
                        case 2:
                            p3 = playerInfo;
                            break;
                        case 3:
                            p4 = playerInfo;
                            break;
                        case 4:
                            p5 = playerInfo;
                            break;
                    }
                    shooterPosition = shooterPosition + 1;
                    squadArrayList.add(playerInfo.getShooterName());
                    customSquadListAdapter = new CustomSquadListAdapter(MainActivity.this, R.layout.list_squad_layout, squadArrayList);
                    customSquadListAdapter.setInfoButtonClickListener(MainActivity.this);
                    customSquadListAdapter.setDeleteButtonListener(MainActivity.this);
                    main_Squad_ListView.setAdapter(customSquadListAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        playersAdapter = new FirebaseListAdapter<PlayerInfo>(MainActivity.this, PlayerInfo.class, android.R.layout.simple_expandable_list_item_1, shootersRef) {
            @Override
            protected void populateView(View v, PlayerInfo model, int position) {
                ((TextView) v).setText(model.getShooterName());
            }
        };

        dropdown.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        playersAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        R.layout.contact_spinner_row_nothing_selected,
                        this));


        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gameDayIndex = life.getGameDayData().size() - 1;
                if (gameDayIndex < 0) {
                    life.getGameDayData().add(new GameDayData());
                    gameDayIndex = 0;
                }

                life.getGameDayData().get(gameDayIndex).createShoot(p1, p2, p3, p4, p5);
                lifeRef.setValue(life);

                int shootIndex = life.getGameDayData().get(gameDayIndex).getShoots().size() - 1;

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(ShootingActivity.KEY_SHOOT_INDEX, shootIndex);
                editor.putInt(ShootingActivity.KEY_DAY_INDEX, gameDayIndex);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, ShootingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void infoButtonClickListener() {
        Intent intent = new Intent(MainActivity.this, ShooterInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteButtonClickListener() {
        shooterPosition = shooterPosition - 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playersAdapter.cleanup();
    }

    private void getPlayersFromFireBase() {
        players = new Players();
        playerRef.child("allPlayers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                players = new Players();
                while (it.hasNext()) {
                    PlayerInfo pInfo = it.next().getValue(PlayerInfo.class);
                    players.addPlayer(pInfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getShootsFromFirebase() {
        life = new Life();
        lifeRef.child("gameDayData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                life = new Life();
                while (it.hasNext()) {
                    GameDayData gameDayData = it.next().getValue(GameDayData.class);
                    life.addGameDay(gameDayData);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void setUpViews() {
        editText = (EditText) findViewById(R.id.main_editText);
        addButton = (Button) findViewById(R.id.main_buttonAdd);
        shootButton = (Button) findViewById(R.id.main_shootButton);
        dropdown = (Spinner) findViewById(R.id.main_spinner);
        main_Squad_ListView = (ListView) findViewById(R.id.main_listView);
    }

    public void setUpFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        shootersRef = firebaseDatabase.getReference("players/allPlayers");
        playerRef = firebaseDatabase.getReference("players");
        lifeRef = firebaseDatabase.getReference("life");
    }


}