package ua.dshafieiev.topjava.persistance.impl;

import ua.dshafieiev.topjava.model.Meal;
import ua.dshafieiev.topjava.persistance.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Dmitriy Shafeyev on 09.11.2017.
 */
public class InMemoryMealRepository implements MealRepository {

    private final AtomicLong counter = new AtomicLong(0);
    private static final Map<Long, Meal> dummyMealsStorage = new ConcurrentHashMap<Long, Meal>() {
        {
            put(1L, new Meal(1L, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            put(2L, new Meal(2L, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            put(3L, new Meal(3L, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            put(4L, new Meal(4L, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            put(5L, new Meal(5L, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            put(6L, new Meal(6L, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }
    };

    public Long storeOrUpdate(Meal meal) {
        Long id = meal.getId();
        if (id != null) {
            checkIfMealExistsOrThrow(id);
            return dummyMealsStorage.put(id, meal).getId();
        }

        Long newId = counter.incrementAndGet();
        meal.setId(newId);
        dummyMealsStorage.put(newId, meal);
        return newId;
    }

    private void checkIfMealExistsOrThrow(Long id) {
        Objects.requireNonNull(dummyMealsStorage.get(id), "There's no such meal");
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(dummyMealsStorage.values());
    }

    @Override
    public Meal delete(Long id) {
        return dummyMealsStorage.remove(id);
    }
}
