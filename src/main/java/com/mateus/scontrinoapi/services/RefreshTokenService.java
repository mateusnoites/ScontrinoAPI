package com.mateus.scontrinoapi.services;

import com.mateus.scontrinoapi.entities.RefreshToken.RefreshToken;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.repositories.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken generate(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(30));
        return repository.save(refreshToken);
    }

    public RefreshToken validate(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new BusinessException("Refresh token inválido"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            repository.delete(refreshToken);
            throw new BusinessException("Refresh token expirado");
        }

        return refreshToken;
    }

}
