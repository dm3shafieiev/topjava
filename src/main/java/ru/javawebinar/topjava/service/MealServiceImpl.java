package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Meal meal, Long userId) {
        repository.save(meal, userId);
    }

    @Override
    public void update(Meal meal, Long userId) {
        checkNotFound(repository.save(meal, userId), "current user can't modify provided meal or such meal doesn't " +
                "exists");
    }

    @Override
    public void delete(Long mealId, Long userId) {
        checkNotFound(repository.delete(mealId, userId), "current user can't delete provided meal");
    }


    @Override
    public List<Meal> getAll(Long userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getFilteredByDateTime(LocalDateTime start, LocalDateTime end, Long userId) {
        return repository.getBetween(start, end, userId);
    }

    @Override
    public Meal get(Long mealId, Long userId) {
        return repository.get(mealId, userId);
    }


}