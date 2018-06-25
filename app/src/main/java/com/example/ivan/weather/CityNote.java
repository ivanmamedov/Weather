package com.example.ivan.weather;

public class CityNote {
    private int id;
    private String name;
    private String type;
    private String season;
    private double firstMonth;
    private double secondMonth;
    private double thirdMonth;

    public CityNote(int id, String name, String type, String season, double firstMonth, double secondMonth, double thirdMonth) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.season = season;
        this.firstMonth = firstMonth;
        this.secondMonth = secondMonth;
        this.thirdMonth = thirdMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public double getFirstMonth() {
        return firstMonth;
    }

    public void setFirstMonth(double firstMonth) {
        this.firstMonth = firstMonth;
    }

    public double getSecondMonth() {
        return secondMonth;
    }

    public void setSecondMonth(double secondMonth) {
        this.secondMonth = secondMonth;
    }

    public double getThirdMonth() {
        return thirdMonth;
    }

    public void setThirdMonth(double thirdMonth) {
        this.thirdMonth = thirdMonth;
    }
}
