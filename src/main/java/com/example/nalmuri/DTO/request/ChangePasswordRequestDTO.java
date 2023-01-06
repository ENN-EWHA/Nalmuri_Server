package com.example.nalmuri.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {
    private String userid;
    private String exPassword;
    private String newPassword;
}
