package com.example.michaeljeffress.project4.Data;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class Game {
    private PlayerData3 shooterOne;
    private PlayerData3 shooterTwo;
    private PlayerData3 shooterThree;
    private PlayerData3 shooterFour;
    private PlayerData3 shooterFive;

    public Game() {
    }

    public Game(PlayerInfo1 p1, PlayerInfo1 p2, PlayerInfo1 p3, PlayerInfo1 p4, PlayerInfo1 p5) {
        shooterOne = new PlayerData3(p1);
        shooterTwo = new PlayerData3(p2);
        shooterThree = new PlayerData3(p3);
        shooterFour = new PlayerData3(p4);
        shooterFive = new PlayerData3(p5);
    }

    public PlayerData3 getShooterOne() {
        return shooterOne;
    }

    public void setShooterOne(PlayerData3 shooterOne) {
        this.shooterOne = shooterOne;
    }

    public PlayerData3 getShooterTwo() {
        return shooterTwo;
    }

    public void setShooterTwo(PlayerData3 shooterTwo) {
        this.shooterTwo = shooterTwo;
    }

    public PlayerData3 getShooterThree() {
        return shooterThree;
    }

    public void setShooterThree(PlayerData3 shooterThree) {
        this.shooterThree = shooterThree;
    }

    public PlayerData3 getShooterFour() {
        return shooterFour;
    }

    public void setShooterFour(PlayerData3 shooterFour) {
        this.shooterFour = shooterFour;
    }

    public PlayerData3 getShooterFive() {
        return shooterFive;
    }

    public void setShooterFive(PlayerData3 shooterFive) {
        this.shooterFive = shooterFive;
    }
}
