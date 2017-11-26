package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.MEAL_COMPARATOR;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Long, Map<Long, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    {
        repository = MealsUtil.MEALS;
    }

    @Override
    public Meal save(Meal meal, Long userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        Map<Long, Meal> usersMeals = repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        usersMeals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(Long id, Long userId) {
        Map<Long, Meal> usersMeal = repository.get(userId);
        return Objects.nonNull(usersMeal) && Objects.nonNull(usersMeal.remove(id));
    }

    @Override
    public Meal get(Long id, Long userId) {
        Map<Long, Meal> usersMeal = repository.get(userId);
        return Objects.nonNull(usersMeal) ? usersMeal.get(id) : null;
    }

    @Override
    public List<Meal> getAll(Long userId) {
        return repository.get(userId).values().stream()
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime start, LocalDateTime end, Long userId) {
        return repository.get(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), start, end))
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

}

