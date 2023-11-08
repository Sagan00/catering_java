package com.example.catering.entity;

public class Bmr {
    public static double calculateBMRValue(int age, String gender, double weight, double height) {
        double bmr = 0.0;
        if (gender.equals("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        bmr = Math.round(bmr * 100)/100;
        return bmr;
    }
}
