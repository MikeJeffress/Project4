package com.example.michaeljeffress.project4.Data;

import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class Players {
    ArrayList<PlayerInfo> allPlayers;

    public Players() {
        allPlayers = new ArrayList<>();
    }

    public ArrayList<PlayerInfo> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<PlayerInfo> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void addPlayer(PlayerInfo playerInfo){
        allPlayers.add(playerInfo);
    }
}
