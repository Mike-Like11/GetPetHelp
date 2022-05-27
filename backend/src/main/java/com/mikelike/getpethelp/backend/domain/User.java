package com.mikelike.getpethelp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String email;
    private String password;
    private Role role;
    @OneToOne(cascade = CascadeType.ALL,mappedBy="user")
    private FullUserInfo fullUserInfo;
    @OneToOne(cascade = CascadeType.ALL,mappedBy="user")
    private ShortUserInfo shortUserInfo;
    public User() {
        role = Role.ROLE_USER;
    }

    public User(FullUserInfo fullUserInfo,
                ShortUserInfo shortUserInfo,
                String email,
                String password) {
        this.fullUserInfo = fullUserInfo;
        this.shortUserInfo = shortUserInfo;
        this.email = email;
        this.password = password;
        role = Role.ROLE_USER;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities
                = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
