package com.mikelike.getpethelp.backend.rest.controller;
import com.mikelike.getpethelp.backend.domain.LoginInput;
import com.mikelike.getpethelp.backend.domain.RegistrationInput;
import com.mikelike.getpethelp.backend.domain.User;
import com.mikelike.getpethelp.backend.service.AuthService;
import com.mikelike.getpethelp.backend.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @SneakyThrows
    @PostMapping("/register")
    User signUp(@ModelAttribute RegistrationInput userInfo) {
        System.out.println(userInfo.toString());
        return authService.register(userInfo);
    }
    @GetMapping("/user/info")
    public User getUserInfo(){
        System.out.println("dsadsadsadsad");
        System.out.println("12345678");
        return userService.getCurrentUser();
    }
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginInput loginInput) {
        return authService.login(loginInput);
    }
}