package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User User);

    void edit(User User);
    User findById(Long id);

    User findByUsername(String username);

    void delete(Long id);
}
