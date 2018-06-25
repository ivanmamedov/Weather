package com.example.ivan.weather.Strategy;

public class StrategyCelsiy implements PatternStrategy {

    @Override
    public double calculate(double temper, String type) {
        double result;
        if (type.equals("kelvin")) {  // Кельвин в Цельсий
            result = temper - 273.15;
            return result;
        } else if (type.equals("fahrenheit")) {  // Фаренгейт в Цельсий
            result = ((temper - 32) * 5) / 9;
            return result;
        }
        return temper;
    }
}