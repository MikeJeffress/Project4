package com.example.michaeljeffress.project4.Data;

import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class Players7 {
    ArrayList<PlayerInfo1> allPlayers;

    public Players7() {
        allPlayers = new ArrayList<>();
    }

    public ArrayList<PlayerInfo1> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<PlayerInfo1> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void addPlayer(PlayerInfo1 playerInfo1){
        allPlayers.add(playerInfo1);
    }
}
