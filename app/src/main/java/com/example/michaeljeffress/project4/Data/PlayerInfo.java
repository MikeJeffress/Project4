package com.example.michaeljeffress.project4.Data;

/**
 * Created by michaeljeffress on 8/19/16.
 */
public class PlayerInfo {
    private String shooterName;
    private long shooterID;

    public PlayerInfo() {
        shooterID = System.currentTimeMillis();
    }

    public PlayerInfo(String shooterName) {
        this.shooterName = shooterName;
    }

    public String getShooterName() {
        return shooterName;
    }

    public void setShooterName(String shooterName) {
        this.shooterName = shooterName;
    }

    public long getShooterID() {
        return shooterID;
    }

    public void setShooterID(long shooterID) {
        this.shooterID = shooterID;
    }
}
