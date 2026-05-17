package com.shah.jwt_auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard(
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        return ResponseEntity.ok(
                "Welcome Admin " + userDetails.getUsername() +
                        "! This is the admin dashboard."
        );
    }

}
