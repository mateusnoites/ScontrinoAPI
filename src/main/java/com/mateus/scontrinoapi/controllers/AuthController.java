package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.AdminRegisterDTO;
import com.mateus.scontrinoapi.dto.LoginResponseDTO;
import com.mateus.scontrinoapi.dto.LoginDTO;
import com.mateus.scontrinoapi.dto.RegisterDTO;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.entities.User.UserRole;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.infra.security.TokenService;
import com.mateus.scontrinoapi.repositories.UserRepository;
import com.mateus.scontrinoapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO data) {
        try {
            userService.create(data);
        } catch(BusinessException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Object> registerAdmin(@RequestBody AdminRegisterDTO data) {
        try {
            userService.createAdmin(data);
        } catch(BusinessException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
