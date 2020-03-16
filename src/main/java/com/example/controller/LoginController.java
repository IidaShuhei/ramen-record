package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/toLogin")
	public String toLogin(Model model, @RequestParam(required = false) String error) {
		if(error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
		}
		return "login";
	}
}
