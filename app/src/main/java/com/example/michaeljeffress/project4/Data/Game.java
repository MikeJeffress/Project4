package com.example.michaeljeffress.project4.Data;

import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class Game {
    private ArrayList<PlayerData> shooters;



    public Game() {
        shooters = new ArrayList<>();
    }

    public void addPlayerToArrayList(PlayerData playerData){
        if (shooters.size()>4){
            return;
        }
        shooters.add(playerData);

    }

    public ArrayList<PlayerData> getShooters() {
        return shooters;
    }

    public void setShooters(ArrayList<PlayerData> shooters) {
        this.shooters = shooters;
    }
}