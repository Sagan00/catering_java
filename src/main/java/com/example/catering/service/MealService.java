package com.example.catering.service;

import com.example.catering.dto.MealDto;
import com.example.catering.entity.Meal;

import java.util.List;

public interface MealService {
    List<Meal> getAllMeals();
    Meal getMealById(Long id);
    Meal getMealByName(String name);
    void saveMeal(MealDto mealDto);
    void updateMeal(MealDto mealDto);
    void deleteMealById(Long id);
}