package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    void create(Meal meal, Long userId);

    void update(Meal meal, Long userId);

    void delete(Long mealId, Long userId);

    List<Meal> getAll(Long id);

    List<Meal> getFilteredByDateTime(LocalDateTime start, LocalDateTime end, Long userId);

    Meal get(Long mealId, Long userId);
}