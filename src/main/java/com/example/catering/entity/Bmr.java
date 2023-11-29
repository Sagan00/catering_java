package com.example.catering.entity;

public class Bmr {
    public static double calculateBMRValue(int age, String gender, double weight, double height, String physicalActivity, String goal) {
        // Validate input values
        if (age <= 0 || weight <= 0 || height <= 0) {
            throw new IllegalArgumentException("Wiek, waga i wzrost muszą mieć wartości większe od 0.");
        }

        double bmr = 0.0;
        if (gender.equals("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        if (physicalActivity.equals("lackOfActivity")) {
            bmr = bmr * 1.2;
        } else if (physicalActivity.equals("lowActivity")) {
            bmr = bmr * 1.4;
        } else if (physicalActivity.equals("averageActivity")) {
            bmr = bmr * 1.6;
        } else if (physicalActivity.equals("highActivity")) {
            bmr = bmr * 1.8;
        } else if (physicalActivity.equals("supremeActivity")) {
            bmr = bmr * 2.0;
        }
        if (goal.equals("gainWeight")) {
            bmr = bmr * 1.2;
        } else if (goal.equals("maintenanceWeight")) {
            bmr = bmr * 1.0;
        } else if (goal.equals("loseWeight")) {
            bmr = bmr * 0.9;
        }

        bmr = Math.round(bmr * 100) / 100;
        return bmr;
    }
}
