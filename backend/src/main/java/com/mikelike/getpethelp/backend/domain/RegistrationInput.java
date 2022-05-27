package com.mikelike.getpethelp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class RegistrationInput {
    private String firstName;
    private String middleName;
    private String lastName;
    private String city;
    private String phone;
    private boolean telegram;
    private boolean whatsApp;
    private boolean viber;
    private int age;
    private String email;
    private String password;
    private MultipartFile avatar;
}