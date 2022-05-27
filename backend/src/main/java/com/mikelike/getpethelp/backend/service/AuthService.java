package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.domain.LoginInput;
import com.mikelike.getpethelp.backend.domain.RegistrationInput;
import com.mikelike.getpethelp.backend.domain.User;
import org.springframework.http.ResponseEntity;

public interface AuthService  {
    User register(RegistrationInput user);
    ResponseEntity<String> login (LoginInput loginInput);
}
