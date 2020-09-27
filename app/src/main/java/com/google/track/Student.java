package com.google.track;

public class Student {
    private int id;
    private String name;
    private float waterVolume;
    private int dayOfWater;

    public Student(int id, String name, float waterVolume, int dayOfWater) {
        this.id = id;
        this.name = name;
        this.waterVolume = waterVolume;
        this.dayOfWater = dayOfWater;
    }

    public float getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(float waterVolume) {
        this.waterVolume = waterVolume;
    }

    public int getDayOfWater() {
        return dayOfWater;
    }

    public void setDayOfWater(int dayOfWater) {
        this.dayOfWater = dayOfWater;
    }
}
