package com.oti.team2.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@GetMapping("/loginForm")
	public String getLogin() {
		return "member/login";
	}
	
	@ResponseBody
	@GetMapping("/auth")
	public Authentication postLogin() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
