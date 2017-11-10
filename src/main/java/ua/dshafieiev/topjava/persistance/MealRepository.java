package ua.dshafieiev.topjava.persistance;

import ua.dshafieiev.topjava.model.Meal;

import java.util.List;

/**
 * Created by Dmitriy Shafeyev on 09.11.2017.
 */
public interface MealRepository {

    Long storeOrUpdate(Meal meal);

    List<Meal> getAll();

    Meal delete(Long id);

}
