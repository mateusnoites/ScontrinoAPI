package com.mateus.scontrinoapi.infra.components;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public String getEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

}
