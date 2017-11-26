package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, Long userId);

    boolean delete(Long id, Long userId);

    List<Meal> getAll(Long userId);

    Meal get(Long id, Long userId);

    List<Meal> getBetween(LocalDateTime start, LocalDateTime end, Long userId);
}
