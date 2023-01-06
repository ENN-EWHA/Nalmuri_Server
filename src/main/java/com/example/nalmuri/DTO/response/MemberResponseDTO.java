package com.example.nalmuri.DTO.response;

import com.example.nalmuri.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private String userid;
    private String email;
    private String userpw;
    private Date birth;




    public static MemberResponseDTO of(User user){
        return MemberResponseDTO.builder()
                .userid(user.getUserid())
                .email(user.getEmail())
                .userpw(user.getUserpw())
                .birth(user.getBirth())
                .build();
    }
}
