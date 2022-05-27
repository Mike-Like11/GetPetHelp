package com.mikelike.getpethelp.backend.service;
import com.mikelike.getpethelp.backend.dao.UserRepository;
import com.mikelike.getpethelp.backend.domain.FullUserInfo;
import com.mikelike.getpethelp.backend.domain.RegistrationInput;
import com.mikelike.getpethelp.backend.domain.ShortUserInfo;
import com.mikelike.getpethelp.backend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public String saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "";
    }
    public FullUserInfo findByShortUserInfo(ShortUserInfo shortUserInfo) {
        System.out.println(shortUserInfo);
        return userRepository.findByShortUserInfo(shortUserInfo).getFullUserInfo();
    }
    public User updateUser(User user){
        Optional<User> updatedUser = userRepository.findById(user.getId());
        if(updatedUser.isPresent()){
            updatedUser.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(updatedUser.get());
        }
        return userRepository.save(user);
    }

    public User getCurrentUser(){
        System.out.println("4444444444");
        System.out.println(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return findByEmail(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }
    public User findByEmail(String email){
        System.out.println(email);
        System.out.println("4444444444");
        return userRepository.findByEmail(email).get();
    }
}
