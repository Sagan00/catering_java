package com.example.catering.service.impl;

import com.example.catering.dto.MealDto;
import com.example.catering.service.MealService;
import com.example.catering.entity.Meal;
import com.example.catering.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository, ModelMapper modelMapper) {
        this.mealRepository = mealRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @Override
    public Meal getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    @Override
    public Meal getMealByName(String name) {
        return mealRepository.findByName(name);
    }
    @Override
    public void saveMeal(MealDto mealDto) {
        Meal meal = modelMapper.map(mealDto, Meal.class);
        mealRepository.save(meal);
    }
    @Override
    public void updateMeal(MealDto mealDto) {
        Optional<Meal> optionalMeal = mealRepository.findById(mealDto.getId());
        optionalMeal.ifPresent(existingMeal -> {
            // Update fields based on mealDto
            existingMeal.setName(mealDto.getName());
            existingMeal.setPicture(mealDto.getPicture());
            existingMeal.setDescription(mealDto.getDescription());
            existingMeal.setCategory(mealDto.getCategory());
            existingMeal.setPrice(mealDto.getPrice());
            existingMeal.setCalories(mealDto.getCalories());

            mealRepository.save(existingMeal);
        });
    }

    @Override
    public void deleteMealById(Long id) {
        mealRepository.deleteById(id);
    }
}
