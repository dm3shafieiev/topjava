package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private AtomicLong counter = new AtomicLong();
    private Map<Long, User> userMap = new HashMap<Long, User>() {
        {
            put(1L, new User(1L, "Dmitriy", "shafeyevdmitriy@gmail.com", "password", Role.ROLE_USER));
            put(2L, new User(2L, "Admin", "admin@gmail.com", "password", Role.ROLE_ADMIN));
            put(3L, new User(3L, "Test", "test@gmail.com", "password", Role.ROLE_USER));
        }
    };

    @Override
    public boolean delete(Long id) {
        log.info("delete {}", id);
        return Objects.isNull(userMap.remove(id));
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(Long id) {
        log.info("get {}", id);
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userMap.values().stream()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userMap.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findAny().orElse(null);
    }
}
