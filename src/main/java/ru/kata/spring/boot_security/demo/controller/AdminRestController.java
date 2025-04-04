package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestController {

    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminRestController(UserServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers () {
        List <User> users = userService.findAll();
        return users !=null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser (@PathVariable Long id) {
        User user = userService.findById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles () {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addUser (@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<HttpStatus> editUser(@PathVariable Long id, @RequestBody User updatedUser) {
        updatedUser.setId(id);
        userService.edit(updatedUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
