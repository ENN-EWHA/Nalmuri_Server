package com.example.nalmuri.controller;


import com.example.nalmuri.DTO.TokenDTO;
import com.example.nalmuri.DTO.request.LoginRequestDTO;
import com.example.nalmuri.DTO.request.MemberRequestDTO;
import com.example.nalmuri.DTO.response.MemberResponseDTO;
import com.example.nalmuri.entity.User;
import com.example.nalmuri.jwt.TokenProvider;
import com.example.nalmuri.repository.UserRepository;
import com.example.nalmuri.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private UserRepository userRepository;
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));

    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto, HttpServletRequest request, HttpServletResponse response) {
        TokenDTO token = authService.login(requestDto,response);
        String refreshToken = token.getRefreshToken();
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(7*24*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
//        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
//                .maxAge(7*24*60*60)
//                .path("/")
//                .secure(true)
//                .sameSite("None")
//                .httpOnly(true)
//                .build();
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestBody  MemberRequestDTO requestDto, HttpServletRequest request, HttpServletResponse response){
        log.info("refesh api");
        Cookie [] yum = request.getCookies();
        log.info(String.valueOf(yum));
        return authService.getRefreshToken(requestDto, request, response);

    }

//    @PostMapping("/refreshToken")
//    public ResponseEntity<TokenDTO> refreshToken(@RequestBody MemberRequestDTO requestDto) throws  Exception{
////        String refreshToken = "";
////
////        Cookie [] cookies = request.getCookies();
////        TokenDTO token = new TokenDTO();
////        if(cookies != null && cookies.length > 0 ) {
////            for(Cookie cookie : cookies) {
////                if(cookie.getName().equals("refreshToken")) {
////                    refreshToken = cookie.getValue();
////                    if(checkClaim(refreshToken)) {
////                        accessToken = getToken(userData.getEmail() , 1);
////                    }else {
////                        throw new InvaildTokenException();
////                    }
////                }
////            }
////        }
//
//        return new ResponseEntity<>(authService.generateRefreshTokenFromAccessToken(accessToken), HttpStatus.OK);

//    }
}
