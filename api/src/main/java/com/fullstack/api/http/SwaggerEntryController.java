package com.fullstack.api.http;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("dev")
public class SwaggerEntryController {

	@GetMapping("/swagger")
	public String swagger() {
		return "redirect:/swagger/index.html";
	}
}
