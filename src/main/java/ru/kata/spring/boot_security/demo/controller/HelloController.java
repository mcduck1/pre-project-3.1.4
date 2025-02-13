package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HelloController {

    private final UserService userServiceImpl;
    private final UserDetails UserDetails;
    private final RoleRepository roleRepository;

    @Autowired
    public HelloController(UserService userServiceImpl, UserDetails UserDetails, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.UserDetails = UserDetails;
        this.roleRepository = roleRepository;
    }


    @GetMapping(value = {"/admin", "/user"})
    public String allUsers(@RequestParam(name = "role", required = false) String selectedRole,
                           @RequestParam(name = "tab", defaultValue = "userTable") String selectedTab,
                           Model model, Principal principal) {

        List<User> userList = userServiceImpl.findAll();
        User currentUser = UserDetails.findByUsername(principal.getName());

        model.addAttribute("users", userList);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("selectedRole", selectedRole);
        model.addAttribute("selectedTab", selectedTab);
        model.addAttribute("user", new User());
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("allRoles", allRoles);

        return "userlist"; // The name of your HTML template
    }

    @PostMapping("admin/addUser")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("roles") Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + roleId));
            roles.add(role);
        }
        userServiceImpl.save(user);
        return "redirect:/admin?tab=userTable";
    }

    @PostMapping()
    public String add(@ModelAttribute("user") User user) {
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/edit/{id}")
    public String editPage(Model model, @PathVariable("id") Long id) {
        User user = userServiceImpl.findById(id);
        System.out.println("Editing user: " + user);
        model.addAttribute("user", user);
        return "userlist";
    }

    @PostMapping("admin/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        user.setId(id);
        userServiceImpl.edit(user);
        return "redirect:/admin?tab=userTable";
    }

    @PostMapping(value = "admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin?tab=userTable";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Debugging: Print the username and password
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Perform authentication (Spring Security will handle this)
        return "redirect:/user";
    }

    @PostMapping("/logout")
    public String logoutPage() {
        return "login";
    }

}