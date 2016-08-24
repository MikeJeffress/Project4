package com.example.michaeljeffress.project4.Data;

import java.util.ArrayList;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class Life6 {
    private ArrayList<GameDayData> gameDayData;

    public Life6() {
        gameDayData = new ArrayList<>();
    }

    public void addGameDay(GameDayData gameDayData){
        this.gameDayData.add(gameDayData);
    }

    public ArrayList<GameDayData> getGameDayData() {
        return gameDayData;
    }

    public void setGameDayData(ArrayList<GameDayData> gameDayData) {
        this.gameDayData = gameDayData;
    }
}
