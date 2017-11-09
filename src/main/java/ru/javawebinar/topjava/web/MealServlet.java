package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.persistance.MealRepository;
import ru.javawebinar.topjava.persistance.impl.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Dmitriy Shafeyev on 09.11.2017.
 */
public class MealServlet extends HttpServlet {

    private static final int CALORIES_LIMIT_PER_DAY = 2000;
    private MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = mealRepository.getAll();
        req.setAttribute("mealsWithExceed", MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX,
                CALORIES_LIMIT_PER_DAY));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
