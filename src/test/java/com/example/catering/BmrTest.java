package com.example.catering;

import com.example.catering.entity.Bmr;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BmrTest {

    @Test
    public void testCalculateBMRValueForMale() {
        double result = Bmr.calculateBMRValue(25, "male", 75.0, 180.0, "averageActivity", "maintenanceWeight");
        assertEquals(2904.0, result, 0.01);
    }

    @Test
    public void testCalculateBMRValueForFemale() {
        double result = Bmr.calculateBMRValue(30, "female", 65.0, 160.0, "lowActivity", "gainWeight");
        assertEquals(2376.0, result, 0.01);
    }

    @Test
    public void testCalculateBMRValueWithZeroAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            Bmr.calculateBMRValue(0, "male", 70.0, 175.0, "averageActivity", "maintenanceWeight");
        });
    }

    @Test
    public void testCalculateBMRValueWithZeroWeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            Bmr.calculateBMRValue(28, "female", 0.0, 165.0, "highActivity", "loseWeight");
        });
    }

    @Test
    public void testCalculateBMRValueWithZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            Bmr.calculateBMRValue(35, "male", 80.0, 0.0, "lowActivity", "gainWeight");
        });
    }

}
