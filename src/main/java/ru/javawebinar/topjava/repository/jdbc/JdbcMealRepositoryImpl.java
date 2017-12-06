package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    @Override
    public Meal save(Meal meal, Long userId) {
        return null;
    }

    @Override
    public boolean delete(Long id, Long userId) {
        return false;
    }

    @Override
    public Meal get(Long id, Long userId) {
        return null;
    }

    @Override
    public List<Meal> getAll(Long userId) {
        return null;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
        return null;
    }
}
