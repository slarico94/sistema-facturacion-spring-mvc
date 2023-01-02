package com.samuelito.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String locale(HttpServletRequest req) {
		String ultimaUrl = req.getHeader("referer");
		return "redirect:".concat(ultimaUrl);
	}
}
