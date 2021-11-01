package me.code.springproducts.user;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public User get(String name) {
        return users.get(name.toLowerCase());
    }

    public void save(User user) {
        users.put(user.getName().toLowerCase(), user);
    }

    public Collection<User> getAll() {
        return users.values();
    }
}
