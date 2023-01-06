package com.example.nalmuri.DTO.request;


import com.example.nalmuri.entity.Authority;
import com.example.nalmuri.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDTO {
    private String userpw;
    private String userid;

    private String email;
    private Date birth;

    public User toMember(PasswordEncoder passwordEncoder){
        return User.builder()
                .userid(userid)
                .userpw(passwordEncoder.encode(userpw))
                .email(email)
                .birth(birth)
                .authority(Authority.ROLE_USER)
                .build();

    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userid, userpw);
    }
}
