package com.example.catering.repository;
import com.example.catering.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findByName(String name);
}
