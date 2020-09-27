package com.google.track;

public class UserInfo {
    private String name;
    private String birthday;
    private String className;
    private String school;
    private int cupOfWater;
    private int height;
    private double weight;
    private String checkInTime;
    private String checkOutTime;
    private String url;

    public UserInfo() {}

    public UserInfo(String name, String birthday, String className, String school, int cupOfWater, int height, double weight, String checkInTime, String checkOutTime, String url) {
        this.name = name;
        this.birthday = birthday;
        this.className = className;
        this.school = school;
        this.cupOfWater = cupOfWater;
        this.height = height;
        this.weight = weight;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getCupOfWater() {
        return cupOfWater;
    }

    public void setCupOfWater(int cupOfWater) {
        this.cupOfWater = cupOfWater;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
