package com.example.nalmuri.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
public class UserUpdateDTO {
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
}
