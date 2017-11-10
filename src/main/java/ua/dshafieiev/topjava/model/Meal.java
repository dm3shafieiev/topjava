package ua.dshafieiev.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal {

    private Long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(Long id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return getCalories() == meal.getCalories() &&
                Objects.equals(getDateTime(), meal.getDateTime()) &&
                Objects.equals(getDescription(), meal.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getCalories());
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", date=" + getDate() +
                ", time=" + getTime() +
                '}';
    }
}
