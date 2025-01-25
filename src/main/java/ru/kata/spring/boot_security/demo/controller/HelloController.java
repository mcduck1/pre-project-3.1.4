package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.tUserService;
import ru.kata.spring.boot_security.demo.model.User;


import java.security.Principal;
import java.util.List;

@Controller
public class HelloController {

	private final UserService userServiceImpl;
	private final tUserService tUserService;

	@Autowired
	public HelloController(UserService userServiceImpl, tUserService tUserService) {
		this.userServiceImpl = userServiceImpl;
		this.tUserService = tUserService;
	}

	@GetMapping(value = "/admin")
	public String allUsers(Model model) {
		List<User> userList = userServiceImpl.getAll();
		model.addAttribute("users", userList);
		return "userlist";
	}

	@GetMapping("admin/add")
	public String addUser(@ModelAttribute("user") User user) {
		return "add";
	}

	@PostMapping()
	public String add(@ModelAttribute ("user") User user) {
		userServiceImpl.add(user);
		return "redirect:/admin";
	}

	@GetMapping("admin/edit/{id}")
	public String editPage (Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userServiceImpl.getById(id));
		return "edit";
	}

	@PostMapping("admin/edit/{id}")
	public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
		userServiceImpl.edit(user);
		return "redirect:/admin";
	}

	@RequestMapping(value="admin/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		userServiceImpl.delete(id);
		return "redirect:/admin";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/user")
	public String showUserProfile(Principal principal, Model model) {
		User user = tUserService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "userProfile";
	}
}