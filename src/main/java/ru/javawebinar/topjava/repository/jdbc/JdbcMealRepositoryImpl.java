package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                  DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", meal.getId());
        parameterSource.addValue("description", meal.getDescription());
        parameterSource.addValue("calories", meal.getCalories());
        parameterSource.addValue("dateTime", meal.getDateTime());
        parameterSource.addValue("user_id", userId);

        if (meal.isNew()) {
            Number number = insertMeal.executeAndReturnKey(parameterSource);
            meal.setId(number.longValue());
        } else {
            int updatedMealsCount = namedParameterJdbcTemplate.update("UPDATE meals SET description=:description, " +
                    "calories=:calories, " +
                    "dateTime=:dateTime WHERE id=:id AND user_id=:user_id", parameterSource);

            if (updatedMealsCount == 0)
                return null;
        }
        return meal;
    }

    @Override
    public boolean delete(Long id, Long userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_Id=?", id, userId) != 0;
    }

    @Override
    public Meal get(Long id, Long userId) {
        List<Meal> query = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_Id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(query);
    }

    @Override
    public List<Meal> getAll(Long userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? " +
                " AND datetime BETWEEN ? AND ? ORDER BY datetime DESC ", ROW_MAPPER, userId, startDate, endDate);
    }
}
