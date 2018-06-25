package com.example.ivan.weather.Strategy;

public class StrategyCelvin implements PatternStrategy {

    @Override
    public double calculate(double temper, String type) {
        double result;
        if (type.equals("celsiy")) { // Цельсий в Кельвин
            result = temper + 273.15;
            return result;
        } else if (type.equals("fahrenheit")) {   // Фаренгейт в Кельвин
            result = ((temper + 459.67) * 5) / 9;
            return result;
        } else
            return temper;
    }
}
