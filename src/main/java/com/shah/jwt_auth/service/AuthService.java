package com.shah.jwt_auth.service;

import com.shah.jwt_auth.dto.AuthResponse;
import com.shah.jwt_auth.dto.LoginRequest;
import com.shah.jwt_auth.dto.RegisterRequest;
import com.shah.jwt_auth.entity.User;
import com.shah.jwt_auth.repository.UserRepository;
import com.shah.jwt_auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        // 1. Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // 2. Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // 3. Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER"); // default role

        // 4. Save to database
        userRepository.save(user);

        // 5. Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // 6. Return token
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        // 1. Authenticate username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. If authentication passes, find user
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException(("User not found!")));

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // 4. Return token
        return new AuthResponse(token);

    }

}
