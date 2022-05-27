package com.mikelike.getpethelp.backend.dao;

import com.mikelike.getpethelp.backend.domain.ShortUserInfo;
import com.mikelike.getpethelp.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByShortUserInfo(ShortUserInfo shortUserInfo);
}
