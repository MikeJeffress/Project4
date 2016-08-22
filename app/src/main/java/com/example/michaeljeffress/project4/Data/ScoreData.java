package com.example.michaeljeffress.project4.Data;

import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/15/16.
 */
public class ScoreData {
    private long dateOfScore;
    private ArrayList<Boolean> recentScoreCard;
    private ArrayList scoreCardList;
    private ArrayList<String> squadList;

    public ScoreData() {
    }


    public long getDateOfScore() {
        return dateOfScore;
    }

    public void setDateOfScore(long dateOfScore) {
        this.dateOfScore = dateOfScore;
    }

    public ArrayList<Boolean> getRecentScoreCard() {
        return recentScoreCard;
    }

    public void setRecentScoreCard(ArrayList<Boolean> recentScoreCard) {
        this.recentScoreCard = recentScoreCard;
    }

    public ArrayList getScoreCardList() {
        return scoreCardList;
    }

    public void setScoreCardList(ArrayList scoreCardList) {
        this.scoreCardList = scoreCardList;
    }

    public ArrayList<String> getSquadList() {
        return squadList;
    }

    public void setSquadList(ArrayList<String> squadList) {
        this.squadList = squadList;
    }
}
