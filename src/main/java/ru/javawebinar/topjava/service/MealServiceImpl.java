package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal get(Long id, Long userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void delete(Long id, Long userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId) {
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List<Meal> getAll(Long userId) {
        return repository.getAll(userId);
    }

    @Override
    public Meal update(Meal meal, Long userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public Meal create(Meal meal, Long userId) {
        return repository.save(meal, userId);
    }
}