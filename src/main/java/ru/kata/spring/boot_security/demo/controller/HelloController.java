package ru.javamentor.spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.spring_boot.model.User;
import ru.javamentor.spring_boot.service.UserService;
import ru.javamentor.spring_boot.service.UserServiceImpl;

import java.util.List;


@Controller
public class HelloController {

	private final UserService userServiceImpl;

	@Autowired
	public HelloController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	@GetMapping(value = "/")
	public String allUsers(Model model) {
		List<User> userList = userServiceImpl.getAll();
		model.addAttribute("users", userList);
		return "user";
	}

	@GetMapping("/add")
	public String addUser(@ModelAttribute("user") User user) {
		return "add";
	}

	@PostMapping()
	public String add(@ModelAttribute ("user") User user) {
		userServiceImpl.add(user);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String editPage (Model model, @PathVariable("id") int id) {
		model.addAttribute("user", userServiceImpl.getById(id));
		return "edit";
	}

	@PostMapping("/edit/{id}")
	public String editUser(@PathVariable("id") int id, @ModelAttribute("user") User user) {
		userServiceImpl.edit(user);
		return "redirect:/";
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") int id) {
		userServiceImpl.delete(id);
		return "redirect:/";
	}
}