package ua.dshafieiev.topjava.web;

import ua.dshafieiev.topjava.model.Meal;
import ua.dshafieiev.topjava.persistance.MealRepository;
import ua.dshafieiev.topjava.persistance.impl.InMemoryMealRepository;
import ua.dshafieiev.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Created by Dmitriy Shafeyev on 09.11.2017.
 */
public class MealServlet extends HttpServlet {

    private static final int CALORIES_LIMIT_PER_DAY = 2000;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isDeleteAction(req)) {
            deleteMealAndRedirect(req, resp);
            return;
        }
        getAllMealAndForwardToJsp(req, resp);
    }

    private boolean isDeleteAction(HttpServletRequest req) {
        String action = req.getParameter("action");
        return !Objects.isNull(action) && action.equals("delete");
    }

    private void deleteMealAndRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = Objects.requireNonNull(req.getParameter("id"), "Need to specify id parameter");
        mealRepository.delete(Long.parseLong(id));
        resp.sendRedirect("/meals");
    }

    private void getAllMealAndForwardToJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = mealRepository.getAll();
        req.setAttribute("mealsWithExceed", MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX,
                CALORIES_LIMIT_PER_DAY));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        performeAction(req, action);
        resp.sendRedirect("/meals");
    }

    private void performeAction(HttpServletRequest req, String action) {
        switch (action) {
            case "store":
                mealRepository.storeOrUpdate(retrieveNewMealFromRequest(req));
                break;
            case "update":
                mealRepository.storeOrUpdate(retrieveExistingMealFromRequest(req));
                break;
            default:
                throw new IllegalArgumentException("there's should be an action");
        }
    }

    private Meal retrieveNewMealFromRequest(HttpServletRequest req) {
        return new Meal(null, LocalDateTime.parse(req.getParameter("dateTime"), dateTimeFormatter), req
                .getParameter("description"), Integer.parseInt(req.getParameter("calories")));
    }

    private Meal retrieveExistingMealFromRequest(HttpServletRequest req) {
        String id = Objects.requireNonNull(req.getParameter("id"));
        return new Meal(Long.valueOf(id), LocalDateTime.parse(req.getParameter("dateTime"), dateTimeFormatter),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
    }
}
