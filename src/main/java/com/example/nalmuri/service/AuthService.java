package com.example.nalmuri.service;

import com.example.nalmuri.DTO.TokenDTO;
import com.example.nalmuri.DTO.request.MemberRequestDTO;
import com.example.nalmuri.DTO.request.NewAccessTokenRequestDTO;
import com.example.nalmuri.DTO.response.MemberResponseDTO;
import com.example.nalmuri.entity.User;
import com.example.nalmuri.exception.ApiRequestException;
import com.example.nalmuri.jwt.TokenProvider;
import com.example.nalmuri.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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

    public TokenDTO login(MemberRequestDTO requestDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

    @Transactional
    public String reIssue(NewAccessTokenRequestDTO tokenRequestDTO, HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());
        String accessToken = "";
        String refreshToken = "";

        Cookie [] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0 ) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    if(tokenProvider.validateToken(refreshToken)) {
                        accessToken = tokenProvider.recreateAccessToken(authentication);
                    }else {
                        throw new ApiRequestException("invalid refresh token");
                    }
                }
            }

        }

        return accessToken;


    }

    public Map<String, String> createRefreshJson(String createdAccessToken){

        Map<String, String> map = new HashMap<>();
        if(createdAccessToken == null){

            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");

            return map;
        }


        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;


    }

}