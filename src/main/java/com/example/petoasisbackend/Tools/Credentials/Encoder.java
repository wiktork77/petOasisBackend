package com.example.petoasisbackend.Tools.Credentials;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
    private final PasswordEncoder passwordEncoder;

    public Encoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public Boolean passwordMatches(String enteredPassword, String password) {
        return passwordEncoder.matches(enteredPassword, password);
    }
}