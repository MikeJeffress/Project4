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

    public void createShoot(PlayerInfo1 p1, PlayerInfo1 p2, PlayerInfo1 p3, PlayerInfo1 p4, PlayerInfo1 p5) {
        Game g = new Game(p1,p2,p3,p4,p5);
        shoots.add(g);
    }
}
