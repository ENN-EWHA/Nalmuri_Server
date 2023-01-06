package com.example.nalmuri.controller;


import com.example.nalmuri.DTO.TokenDTO;
import com.example.nalmuri.DTO.request.LoginRequestDTO;
import com.example.nalmuri.DTO.request.MemberRequestDTO;
import com.example.nalmuri.DTO.response.MemberResponseDTO;
import com.example.nalmuri.entity.User;
import com.example.nalmuri.repository.UserRepository;
import com.example.nalmuri.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));

    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
