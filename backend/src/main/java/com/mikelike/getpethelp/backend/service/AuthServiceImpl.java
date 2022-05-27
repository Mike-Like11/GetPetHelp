package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.config.JWTUtil;
import com.mikelike.getpethelp.backend.dao.UserRepository;
import com.mikelike.getpethelp.backend.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements UserDetailsService, AuthService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("com.example.demo.User.User with email {0} cannot be found.", username)));
    }

    @Override
    public ResponseEntity<String> login(LoginInput loginInput) {
        Optional<User> user = userRepository.findByEmail(loginInput.getEmail());
        if (user.isPresent()) {
            if (bCryptPasswordEncoder.matches(loginInput.getPassword(), user.get().getPassword())) {
                String token = jwtUtil.generateToken(loginInput.getEmail());
                return new ResponseEntity(token, HttpStatus.OK);
            } else {
                return new ResponseEntity("Неправильный ввод данных", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity("Неправильный ввод данных", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User register(RegistrationInput user) {
        User user2 = new User();
        user2.setEmail(user.getEmail());
        FullUserInfo fullUserInfo = new FullUserInfo();
        fullUserInfo.setUser(user2);
        fullUserInfo.setAge(user.getAge());
        fullUserInfo.setCity(user.getCity());
        fullUserInfo.setMiddleName(user.getMiddleName());
        fullUserInfo.setViber(user.isViber());
        fullUserInfo.setTelegram(user.isTelegram());
        fullUserInfo.setWhatsApp(user.isWhatsApp());
        ShortUserInfo shortUserInfo = new ShortUserInfo();
        shortUserInfo.setUser(user2);
        shortUserInfo.setFirstName(user.getFirstName());
        shortUserInfo.setLastName(user.getLastName());
        shortUserInfo.setPhone(user.getPhone());
        shortUserInfo.setAvatarUrl(cloudinaryService.uploadFile(user.getAvatar()));
        String cryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user2.setPassword(cryptedPassword);
        user2.setFullUserInfo(fullUserInfo);
        user2.setShortUserInfo(shortUserInfo);
        System.out.println(shortUserInfo.toString());
        return userRepository.save(user2);
    }
}
