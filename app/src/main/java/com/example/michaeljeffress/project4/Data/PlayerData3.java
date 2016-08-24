package com.example.michaeljeffress.project4.Data;

/**
 * Created by michaeljeffress on 8/15/16.
 */

public class PlayerData3 {
    private LaneData2 station1;
    private LaneData2 station2;
    private LaneData2 station3;
    private LaneData2 station4;
    private LaneData2 station5;
    private int totalScore;
    private int streakIn;
    private int streakOut;
    private int streakDuring;

    private PlayerInfo1 shooterInfo;


    public PlayerData3(PlayerInfo1 playerInfo1) {
        shooterInfo = playerInfo1;
        station1 = new LaneData2();
        station2 = new LaneData2();
        station3 = new LaneData2();
        station4 = new LaneData2();
        station5 = new LaneData2();
        totalScore = 0;
        streakIn = 0;
        streakOut = 0;
        streakDuring = 0;
    }

    public PlayerData3() {
    }

    public LaneData2 getStation1() {
        return station1;
    }

    public void setStation1(LaneData2 station1) {
        this.station1 = station1;
    }

    public LaneData2 getStation2() {
        return station2;
    }

    public void setStation2(LaneData2 station2) {
        this.station2 = station2;
    }

    public LaneData2 getStation3() {
        return station3;
    }

    public void setStation3(LaneData2 station3) {
        this.station3 = station3;
    }

    public LaneData2 getStation4() {
        return station4;
    }

    public void setStation4(LaneData2 station4) {
        this.station4 = station4;
    }

    public LaneData2 getStation5() {
        return station5;
    }

    public void setStation5(LaneData2 station5) {
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

    public PlayerInfo1 getShooterInfo() {
        return shooterInfo;
    }

    public void setShooterInfo(PlayerInfo1 shooterInfo) {
        this.shooterInfo = shooterInfo;
    }
}
