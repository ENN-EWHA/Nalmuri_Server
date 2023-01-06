package com.example.nalmuri.service;

import com.example.nalmuri.DTO.TokenDTO;
import com.example.nalmuri.DTO.request.MemberRequestDTO;
import com.example.nalmuri.DTO.response.MemberResponseDTO;
import com.example.nalmuri.entity.User;
import com.example.nalmuri.exception.ApiRequestException;
import com.example.nalmuri.jwt.TokenProvider;
import com.example.nalmuri.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDTO signup(MemberRequestDTO requestDto) {
        if (userRepository.existsByUserid(requestDto.getUserid())){
            throw new ApiRequestException("동일한 아이디로 가입된 계정이 이미 존재합니다.");
        }

        User user = requestDto.toMember(passwordEncoder);
        return MemberResponseDTO.of(userRepository.save(user));
    }

    public TokenDTO login(MemberRequestDTO requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }
}