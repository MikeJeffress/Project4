package com.example.michaeljeffress.project4.Data;

import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class GameDayData {
    private ArrayList<Game> shoots; // 0 or rounds.
    private long dateOfScoreCard;

    public GameDayData() {
        shoots = new ArrayList<>();
        dateOfScoreCard = System.currentTimeMillis();
    }

    public ArrayList<Game> getShoots() {
        return shoots;
    }

    public void setShoots(ArrayList<Game> shoots) {
        this.shoots = shoots;
    }

    public long getDateOfScoreCard() {
        return dateOfScoreCard;
    }

    public void setDateOfScoreCard(long dateOfScoreCard) {
        this.dateOfScoreCard = dateOfScoreCard;
    }

    public void createShoot(PlayerInfo p1, PlayerInfo p2, PlayerInfo p3, PlayerInfo p4, PlayerInfo p5) {
        Game g = new Game();
        addPlayerToList(p1, g);
        addPlayerToList(p2, g);
        addPlayerToList(p3, g);
        addPlayerToList(p4, g);
        addPlayerToList(p5, g);
        shoots.add(g);
    }

    private void addPlayerToList(PlayerInfo playerInfo, Game g){
        if(playerInfo != null){
            g.addPlayerToArrayList(new PlayerData(playerInfo));
        }
    }
}
