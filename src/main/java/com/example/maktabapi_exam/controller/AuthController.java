package com.example.maktabapi_exam.controller;


import com.example.maktabapi_exam.dto.LoginDTO;
import com.example.maktabapi_exam.security.JwtProvider;
import com.example.maktabapi_exam.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO) {
        UserDetails userDetails = authService.loadUserByUsername(loginDTO.getUserName());

        if (userDetails != null) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            String token = jwtProvider.generateToken(loginDTO.getUserName());
            return ResponseEntity.ok().body(token);
        } else {
            return ResponseEntity.status(401).body("Aka adashdiz");
        }
    }
}
