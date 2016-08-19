package com.example.michaeljeffress.project4.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/15/16.
 */
public class ShooterData {
    private String shooter;
    private Array recentScore;
    private ArrayList scoreLists;
    private ArrayList<String> squadList;

    public ShooterData() {
    }

    public String getShooter() {
        return shooter;
    }

    public void setShooter(String shooter) {
        this.shooter = shooter;
    }

    public ArrayList<String> getSquadList() {
        return squadList;
    }

    public void setSquadList(ArrayList<String> squadList) {
        this.squadList = squadList;
    }

    public Array getRecentScore() {
        return recentScore;
    }

    public void setRecentScore(Array recentScore) {
        this.recentScore = recentScore;
    }

    public ArrayList getScoreLists() {
        return scoreLists;
    }

    public void setScoreLists(ArrayList scoreLists) {
        this.scoreLists = scoreLists;
    }
}
