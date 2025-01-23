package ru.javamentor.spring_boot.dao;


import ru.javamentor.spring_boot.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAll();
    void add(User user);
    void edit(User user);
    User getById(int id);
    void delete(int id);
}
