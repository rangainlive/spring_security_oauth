package com.pi.spring_security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService service;
	
	
	@GetMapping("/home")
	public String getWelcomeHome() {
		return "Welcome to Spring Security Services Home";
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request) {
		log.info("Received Request: {}", request); // Debug log
		log.info("Email: "+request.getEmail());
		return ResponseEntity.ok(service.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(service.authenticate(request));
	}

}
