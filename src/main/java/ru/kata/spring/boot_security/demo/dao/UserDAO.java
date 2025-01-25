package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAll();
    void add(User user);
    void edit(User user);
    User getById(Long id);
    void delete(Long id);
}
