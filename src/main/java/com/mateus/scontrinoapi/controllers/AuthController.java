package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.*;
import com.mateus.scontrinoapi.entities.RefreshToken.RefreshToken;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.infra.security.TokenService;
import com.mateus.scontrinoapi.repositories.UserRepository;
import com.mateus.scontrinoapi.services.RefreshTokenService;
import com.mateus.scontrinoapi.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        String accessToken = tokenService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.generate(user);

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody RefreshRequestDTO data) {
        RefreshToken refreshToken = refreshTokenService.validate(data.refreshToken());
        String newAccessToken = tokenService.generateToken(refreshToken.getUser());

        return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, data.refreshToken()));
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
