package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.domain.FullUserInfo;
import com.mikelike.getpethelp.backend.domain.ShortUserInfo;
import com.mikelike.getpethelp.backend.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> allUsers();
    String saveUser(User user);
    FullUserInfo findByShortUserInfo(ShortUserInfo shortUserInfo);
    User updateUser(User user);
    User getCurrentUser();
    User findByEmail(String email);
}
