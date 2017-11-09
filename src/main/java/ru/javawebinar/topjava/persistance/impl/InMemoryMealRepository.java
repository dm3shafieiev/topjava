package ru.javawebinar.topjava.persistance.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.persistance.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dmitriy Shafeyev on 09.11.2017.
 */
public class InMemoryMealRepository implements MealRepository {

    private static final Map<Long, Meal> dummyMealsStorage = new ConcurrentHashMap<Long, Meal>() {
        {
            put(1L, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            put(2L, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            put(3L, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            put(4L, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            put(5L, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            put(6L, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }
    };

    public Long store(Meal meal) {
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(dummyMealsStorage.values());
    }

    @Override
    public Meal update(Long id) {
        return null;
    }

    @Override
    public Meal delete(Long id) {
        return null;
    }
}
