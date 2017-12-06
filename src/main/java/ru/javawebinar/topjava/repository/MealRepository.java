package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal, Long userId);

    // false if meal do not belong to userId
    boolean delete(Long id, Long userId);

    // null if meal do not belong to userId
    Meal get(Long id, Long userId);

    // ORDERED dateTime desc
    List<Meal> getAll(Long userId);

    // ORDERED dateTime desc
    List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, Long userId);
}
