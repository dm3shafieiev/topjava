package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.dto.MealWithExceed;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public void create(Meal meal) {
        log.debug("create new meal {}", meal);
        checkNew(meal);
        service.create(meal, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        log.debug("update meal {}", meal);
        service.update(meal, AuthorizedUser.id());
    }

    public void delete(Long mealId) {
        log.debug("delete meal {}", mealId);
        service.delete(mealId, AuthorizedUser.id());
    }

    public Meal get(Long mealId) {
        log.debug("get user's meal by id {}", mealId);
        return service.get(mealId, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        log.debug("get all users's meals");
        List<Meal> meals = service.getAll(AuthorizedUser.id());
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredByDateTime(LocalDateTime start, LocalDateTime end) {
        log.debug("get user's meal filtered by date range start from {} to {}", start, end);

        return MealsUtil.getWithExceeded(service.getFilteredByDateTime(start, end, AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay());
    }
}