package com.example.ivan.weather.Strategy;

public class Algoritm {
    private PatternStrategy patternStrategy;

    public Algoritm(PatternStrategy patternStrategy){
        this.patternStrategy = patternStrategy;
    }

    public PatternStrategy getPatternStrategy() {
        return patternStrategy;
    }

    public void setPatternStrategy(PatternStrategy patternStrategy) {
        this.patternStrategy = patternStrategy;
    }

    public double calculate(double temper, String type){
        return  patternStrategy.calculate(temper, type);
    }
}
