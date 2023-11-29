package com.example.catering.controller;

import org.springframework.ui.Model;
import com.example.catering.dto.MealDto;
import com.example.catering.entity.Meal;
import com.example.catering.service.MealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MealsController {
    private MealService mealService;

    public MealsController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public String showAllMeals(Model model) {
        List<Meal> meals = mealService.getAllMeals();
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/add")
    public String showAddMealForm(Model model) {
        MealDto meal = new MealDto();
        model.addAttribute("meal", meal);
        return "add_meal";
    }

    @PostMapping("/add/save")
    public String addMeal(@Valid @ModelAttribute("meal") MealDto mealDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("meal", mealDto);
            return "add_meal";
        }

        mealService.saveMeal(mealDto);
        return "redirect:/menu";
    }

    @GetMapping("/edit/{id}")
    public String showEditMealForm(@PathVariable Long id, Model model) {
        Meal meal = mealService.getMealById(id);
        model.addAttribute("meal", meal);
        return "edit_meal";
    }

    @PostMapping("/edit/save")
    public String editMeal(@Valid @ModelAttribute("meal") MealDto mealDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("meal", mealDto);
            return "edit_meal";
        }

        mealService.updateMeal(mealDto);
        return "redirect:/menu";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable Long id) {
        mealService.deleteMealById(id);
        return "redirect:/menu";
    }
}
