package com.example.michaeljeffress.project4.Data;

/**
 * Created by michaeljeffress on 8/22/16.
 */
public class LaneData {
    private boolean s1;
    private boolean s2;
    private boolean s3;
    private boolean s4;
    private boolean s5;
    private int score;

    public LaneData() {
        score = 0;
    }

    public boolean isS1() {
        return s1;
    }

    public void setS1(boolean s1) {
        this.s1 = s1;
    }

    public boolean isS2() {
        return s2;
    }

    public void setS2(boolean s2) {
        this.s2 = s2;
    }

    public boolean isS3() {
        return s3;
    }

    public void setS3(boolean s3) {
        this.s3 = s3;
    }

    public boolean isS4() {
        return s4;
    }

    public void setS4(boolean s4) {
        this.s4 = s4;
    }

    public boolean isS5() {
        return s5;
    }

    public void setS5(boolean s5) {
        this.s5 = s5;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
