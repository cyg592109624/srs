package com.sunrise.srs.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.sunrise.srs.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class WorkOut implements Parcelable {
    private int workOutMode = Constant.MODE_QUICK_START;
    private String workOutModeName = Constant.WORK_OUT_MODE_QUICK_START;
    /**
     * 0代表男；1代表女
     */
    private int gender = Constant.GENDER_MALE;
    private String age = "20";

    /**
     * 以选择单位制度来决定单位
     */
    private String weight = "70";
    /**
     * 这里以min为单位
     */
    private String time = "0";

    /**
     * 已经运动时间 以秒为单位
     */
    private String runningTime = "0";

    /**
     * 目标距离 以选择单位制度来决定单位
     */
    private String distance = "0";

    /**
     * 已经运行距离
     */
    private String runningDistance = "0";

    /**
     * 目标消耗calories
     */
    private String calories = "0";

    /**
     * 已经消耗calories
     */
    private String runningCalories = "0";

    private int goalType = Constant.MODE_GOAL_TIME;

    private String hrc = "80";

    private int hrcType = Constant.MODE_HRC_TYPE_TG;

    private int vrType = Constant.MODE_VR_TYPE_VR1;

    private List<Level> levelList = new ArrayList<>();

    /**
     * 段数移动次数
     */
    private int runningLevelCount=0;

    public WorkOut() {

    }

    protected WorkOut(Parcel in) {
        workOutMode = in.readInt();
        workOutModeName = in.readString();
        gender = in.readInt();
        age = in.readString();
        weight = in.readString();
        time = in.readString();
        runningTime = in.readString();
        distance = in.readString();
        runningDistance = in.readString();
        calories = in.readString();
        runningCalories = in.readString();
        goalType = in.readInt();
        hrc = in.readString();
        hrcType = in.readInt();
        vrType = in.readInt();
        in.readTypedList(levelList, Level.CREATOR);
        runningLevelCount= in.readInt();
    }

    public static final Creator<WorkOut> CREATOR = new Creator<WorkOut>() {
        @Override
        public WorkOut createFromParcel(Parcel in) {
            return new WorkOut(in);
        }

        @Override
        public WorkOut[] newArray(int size) {
            return new WorkOut[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(workOutMode);
        parcel.writeString(workOutModeName);
        parcel.writeInt(gender);
        parcel.writeString(age);
        parcel.writeString(weight);
        parcel.writeString(time);
        parcel.writeString(runningTime);
        parcel.writeString(distance);
        parcel.writeString(runningDistance);
        parcel.writeString(calories);
        parcel.writeString(runningCalories);
        parcel.writeInt(goalType);
        parcel.writeString(hrc);
        parcel.writeInt(hrcType);
        parcel.writeInt(vrType);
        parcel.writeTypedList(levelList);
        parcel.writeInt(runningLevelCount);
    }

    public int getWorkOutMode() {
        return workOutMode;
    }

    public void setWorkOutMode(int workOutMode) {
        this.workOutMode = workOutMode;
    }

    public String getWorkOutModeName() {
        return workOutModeName;
    }

    public void setWorkOutModeName(String workOutModeName) {
        this.workOutModeName = workOutModeName;
    }


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        if (gender == 0) {
            this.gender = 0;
        } else {
            this.gender = 1;
        }
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRunningDistance() {
        return runningDistance;
    }

    public void setRunningDistance(String runningDistance) {
        this.runningDistance = runningDistance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getRunningCalories() {
        return runningCalories;
    }

    public void setRunningCalories(String runningCalories) {
        this.runningCalories = runningCalories;
    }

    public int getGoalType() {
        return goalType;
    }

    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public String getHrc() {
        return hrc;
    }

    public void setHrc(String hrc) {
        this.hrc = hrc;
    }

    public int getHrcType() {
        return hrcType;
    }

    public void setHrcType(int hrcType) {
        this.hrcType = hrcType;
    }

    public int getVrType() {
        return vrType;
    }

    public void setVrType(int vrType) {
        this.vrType = vrType;
    }

    public List<Level> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Level> levelList) {
        this.levelList = levelList;
    }

    public int getRunningLevelCount() {
        return runningLevelCount;
    }

    public void setRunningLevelCount(int runningLevelCount) {
        this.runningLevelCount = runningLevelCount;
    }
}
