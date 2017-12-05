package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Dmitriy Shafeyev on 04.12.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
})
@RunWith(SpringRunner.class)
@Sql(scripts = {"classpath:db/populateDB.sql"})
public class MealServiceImplTest {

    @Autowired
    private MealService mealService;
    private static final long MEAL_ID = 100002L;
    private static final Meal MEAL = new Meal(MEAL_ID, LocalDateTime.parse("2017-11-29 13:25:27",
            DateTimeUtil.DATE_TIME_FORMATTER), "обед", 1000);

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertThat(meal, is(MEAL));
    }

    @Test
    public void getOthersUserMeal() throws Exception {
        Assertions.assertThatThrownBy(() -> mealService.get(MEAL_ID, UserTestData.ADMIN_ID))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void delete() {
        assertThat(mealService.get(MEAL_ID, USER_ID), notNullValue());
        mealService.delete(MEAL_ID, USER_ID);
        Assertions.assertThatThrownBy(() -> mealService.get(MEAL_ID, USER_ID))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleteOthersUserMeal() throws Exception {
        Assertions.assertThatThrownBy(() -> mealService.delete(MEAL_ID, ADMIN_ID))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {

    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = mealService.getAll(USER_ID);
        List<Meal> expectedList = MealsUtil.MEALS.get(USER_ID);
        Assertions.assertThat(mealService.getAll(USER_ID)).contains(expectedList.toArray(new Meal[all.size()]));
    }

    @Test
    public void updateOthersUserMeal() throws Exception {
        Assertions.assertThatThrownBy(() -> mealService.update(MEAL, ADMIN_ID))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void update() {
        MEAL.setCalories(100);
        MEAL.setDescription("dinner");
        Meal updated = mealService.update(MEAL, USER_ID);
        assertEquals(updated, mealService.get(MEAL_ID, USER_ID));
    }

    @Test
    public void create() {
        final Meal newMeal = new Meal(null, LocalDateTime.parse("2017-12-29 13:25:27",
                DateTimeUtil.DATE_TIME_FORMATTER), "обед", 1000);
        Meal meal = mealService.create(newMeal, 100000L);
        newMeal.setId(100011L);
        assertEquals(meal, newMeal);
    }

}