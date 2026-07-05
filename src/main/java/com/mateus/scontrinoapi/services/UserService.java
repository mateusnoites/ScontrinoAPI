package com.mateus.scontrinoapi.services;

import com.mateus.scontrinoapi.dto.AdminRegisterDTO;
import com.mateus.scontrinoapi.dto.RegisterDTO;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.entities.User.UserRole;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByEmail(username);
    }

    public User create(RegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null)
            throw new BusinessException("Usuário já existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword);
        newUser.setRole(UserRole.ROLE_USER);

        return this.repository.save(newUser);
    }

    public User createAdmin(AdminRegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null)
            throw new BusinessException("Usuário já existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword);

        return this.repository.save(newUser);
    }

}
