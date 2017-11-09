package ru.javawebinar.topjava.persistance;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Dmitriy Shafeyev on 09.11.2017.
 */
public interface MealRepository {

    Long store(Meal meal);

    List<Meal> getAll();

    Meal update(Long id);

    Meal delete(Long id);

}
