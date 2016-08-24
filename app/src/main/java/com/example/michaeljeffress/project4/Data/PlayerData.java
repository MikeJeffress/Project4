package com.example.michaeljeffress.project4.Data;

/**
 * Created by michaeljeffress on 8/15/16.
 */

public class PlayerData {
    private LaneData station1;
    private LaneData station2;
    private LaneData station3;
    private LaneData station4;
    private LaneData station5;
    private int totalScore;
    private int streakIn;
    private int streakOut;
    private int streakDuring;

    private PlayerInfo shooterInfo;


    public PlayerData(PlayerInfo playerInfo) {
        shooterInfo = playerInfo;
        station1 = new LaneData();
        station2 = new LaneData();
        station3 = new LaneData();
        station4 = new LaneData();
        station5 = new LaneData();
        totalScore = 0;
        streakIn = 0;
        streakOut = 0;
        streakDuring = 0;
    }

    public PlayerData() {
    }

    public LaneData getStation1() {
        return station1;
    }

    public void setStation1(LaneData station1) {
        this.station1 = station1;
    }

    public LaneData getStation2() {
        return station2;
    }

    public void setStation2(LaneData station2) {
        this.station2 = station2;
    }

    public LaneData getStation3() {
        return station3;
    }

    public void setStation3(LaneData station3) {
        this.station3 = station3;
    }

    public LaneData getStation4() {
        return station4;
    }

    public void setStation4(LaneData station4) {
        this.station4 = station4;
    }

    public LaneData getStation5() {
        return station5;
    }

    public void setStation5(LaneData station5) {
        this.station5 = station5;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getStreakIn() {
        return streakIn;
    }

    public void setStreakIn(int streakIn) {
        this.streakIn = streakIn;
    }

    public int getStreakOut() {
        return streakOut;
    }

    public void setStreakOut(int streakOut) {
        this.streakOut = streakOut;
    }

    public int getStreakDuring() {
        return streakDuring;
    }

    public void setStreakDuring(int streakDuring) {
        this.streakDuring = streakDuring;
    }

    public PlayerInfo getShooterInfo() {
        return shooterInfo;
    }

    public void setShooterInfo(PlayerInfo shooterInfo) {
        this.shooterInfo = shooterInfo;
    }
}
