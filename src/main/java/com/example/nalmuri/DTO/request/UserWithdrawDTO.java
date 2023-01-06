package com.example.nalmuri.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class UserWithdrawDTO {
    private String checkPassword;

}
