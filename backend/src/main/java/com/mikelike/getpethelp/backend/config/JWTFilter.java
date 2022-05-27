package com.mikelike.getpethelp.backend.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mikelike.getpethelp.backend.service.AuthService;
import com.mikelike.getpethelp.backend.service.AuthServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final AuthServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;

    public JWTFilter(AuthServiceImpl userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            System.out.println(jwt);
            if(jwt.isEmpty()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            }else {
                try{
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    System.out.println("qweqweqwe");
                    System.out.println(email);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    System.out.println(userDetails.getAuthorities());
                    System.out.println(userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                }catch(JWTVerificationException exc){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}