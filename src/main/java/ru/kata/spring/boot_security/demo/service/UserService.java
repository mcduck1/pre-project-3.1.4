package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> getAll();
    void add(User User);

    void edit(User User);
    User getById(Long id);

    void delete(Long id);
}
