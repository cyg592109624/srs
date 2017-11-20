package com.sunrise.srs.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class Level implements Parcelable {
    private int level;

    public Level(){
    }

    protected Level(Parcel in) {
        level = in.readInt();
    }

    public static final Creator<Level> CREATOR = new Creator<Level>() {
        @Override
        public Level createFromParcel(Parcel in) {
            return new Level(in);
        }

        @Override
        public Level[] newArray(int size) {
            return new Level[size];
        }
    };

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(level);
    }
}
