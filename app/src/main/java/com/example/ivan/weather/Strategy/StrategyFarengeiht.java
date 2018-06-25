package com.example.ivan.weather.Strategy;

public class StrategyFarengeiht implements PatternStrategy {

    @Override
    public double calculate(double temper, String type) {
        double result;
        if (type.equals("celsiy")){  // Цельсий в Фаренгейт
            result = ((temper * 9) / 5) + 32;
            return result;
        } else if (type.equals("kelvin")){   // Кельвин в Фаренгейт
            result = (9 * temper) / 5 - 459.67;
            return result;
        } else
            return temper;

    }
}
