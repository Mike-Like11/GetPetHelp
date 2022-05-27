package com.mikelike.getpethelp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginInput {
    private String email;
    private String password;
}