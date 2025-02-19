package com.pi.spring_security.home;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/welcome")
	public ResponseEntity<String> getHomeWelcomePage() {
		return ResponseEntity.ok("Welcome to Authenticated Services...");
	}

}
