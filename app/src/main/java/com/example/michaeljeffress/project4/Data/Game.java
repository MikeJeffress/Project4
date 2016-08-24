package com.example.michaeljeffress.project4.Data;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class Game {
    private PlayerData shooter1;
    private PlayerData shooter2;
    private PlayerData shooter3;
    private PlayerData shooter4;
    private PlayerData shooter5;

    public Game() {
    }

    public Game(PlayerInfo p1, PlayerInfo p2, PlayerInfo p3, PlayerInfo p4, PlayerInfo p5) {
        shooter1 = new PlayerData(p1);
        shooter2 = new PlayerData(p2);
        shooter3 = new PlayerData(p3);
        shooter4 = new PlayerData(p4);
        shooter5 = new PlayerData(p5);
    }

    public PlayerData getShooter1() {
        return shooter1;
    }

    public void setShooter1(PlayerData shooter1) {
        this.shooter1 = shooter1;
    }

    public PlayerData getShooter2() {
        return shooter2;
    }

    public void setShooter2(PlayerData shooter2) {
        this.shooter2 = shooter2;
    }

    public PlayerData getShooter3() {
        return shooter3;
    }

    public void setShooter3(PlayerData shooter3) {
        this.shooter3 = shooter3;
    }

    public PlayerData getShooter4() {
        return shooter4;
    }

    public void setShooter4(PlayerData shooter4) {
        this.shooter4 = shooter4;
    }

    public PlayerData getShooter5() {
        return shooter5;
    }

    public void setShooter5(PlayerData shooter5) {
        this.shooter5 = shooter5;
    }
}
