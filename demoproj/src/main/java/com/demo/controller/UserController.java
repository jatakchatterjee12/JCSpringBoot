package com.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.demo.dao.UserRepository;
import com.demo.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {

		String username = principal.getName();
		System.out.println(username);

		// get the user by username(email)
		User user = this.userRepository.getUserByUserName(username);

		System.out.println(user);

		// send data to frontend
		model.addAttribute("user", user);

		return "normal/user_dash";
	}
}
