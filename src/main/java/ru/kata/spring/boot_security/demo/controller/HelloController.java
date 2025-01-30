package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;


import java.security.Principal;
import java.util.List;

@Controller
public class HelloController {

	private final UserService userServiceImpl;
	private final UserDetails UserDetails;

	@Autowired
	public HelloController(UserService userServiceImpl, UserDetails UserDetails) {
		this.userServiceImpl = userServiceImpl;
		this.UserDetails = UserDetails;
	}

	@GetMapping(value = "/admin")
	public String allUsers(Model model) {
		List<User> userList = userServiceImpl.findAll();
		model.addAttribute("users", userList);
		return "userlist";
	}

	@GetMapping("admin/add")
	public String addUser(@ModelAttribute("user") User user) {
		return "add";
	}

	@PostMapping()
	public String add(@ModelAttribute ("user") User user) {
		userServiceImpl.save(user);
		return "redirect:/admin";
	}

	@GetMapping("admin/edit/{id}")
	public String editPage (Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userServiceImpl.findById(id));
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
		User user = UserDetails.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "userProfile";
	}
}