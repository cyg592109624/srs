package com.sunrise.srs.bean;

/**
 * Created by ChuHui on 2017/9/28.
 */

public class LevelColumn {
    private float toX1, toX2, toY1, toY2;
    private int level=0;

    public LevelColumn() {

    }

    public void setXY(float x1, float y1, float x2, float y2) {
        toX1 = x1;
        toY1 = y1;
        toX2 = x2;
        toY2 = y2;
    }

    public float getToX1() {
        return toX1;
    }

    public void setToX1(float toX1) {
        this.toX1 = toX1;
    }

    public float getToX2() {
        return toX2;
    }

    public void setToX2(float toX2) {
        this.toX2 = toX2;
    }

    public float getToY1() {
        return toY1;
    }

    public void setToY1(float toY1) {
        this.toY1 = toY1;
    }

    public float getToY2() {
        return toY2;
    }

    public void setToY2(float toY2) {
        this.toY2 = toY2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
