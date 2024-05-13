package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.dao.UserRepository;
import com.demo.entities.User;
import com.demo.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo; 
	
	@RequestMapping("/")
	public String home(Model model) {
		
		model.addAttribute("title", "Home- Just for Demo");
		return "home";
	}
	

	@RequestMapping("/about")
	public String about(Model model) {
		
		model.addAttribute("title", "About- Just for Demo");
		return "about";
	}
	
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title", "Register- Just for Demo");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	//handler for registering user
	@PostMapping("/do_register")
	public String register(@Valid @ModelAttribute("user") User user, Model model, BindingResult result, HttpSession session) {
		
		try {
			
			if(result.hasErrors()) {
				System.out.println("ERROR" + result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			

			user.setRole("ROLE_USER");
			user.setActive(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			User saveUser = this.userRepo.save(user);
			model.addAttribute("user", new User());
			
			System.out.println(user);
			
			
			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
			return "signup";
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong", "alert-danger"));
			return "signup";
		}
		
		
		
	}
	
	
	
	//login handler
	@GetMapping("/signin")
	public String login(Model model) {
		
		model.addAttribute("title", "This is Login page");
		
		return "login";
	}
}
