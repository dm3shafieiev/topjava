package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Long, User> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(100);

    public void init() {
        repository.clear();
        repository.put(UserTestData.USER_ID, USER);
        repository.put(UserTestData.ADMIN_ID, ADMIN);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(Long id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(Long id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        return repository.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
