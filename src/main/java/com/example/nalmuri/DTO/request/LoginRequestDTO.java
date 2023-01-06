package com.example.nalmuri.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequestDTO {
    private String userpw;
    private String userid;
}
