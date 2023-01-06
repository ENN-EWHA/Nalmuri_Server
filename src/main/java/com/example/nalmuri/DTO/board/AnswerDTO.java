package com.example.nalmuri.DTO.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class AnswerDTO {
    private String userid;
    private @JsonFormat(pattern = "yyyy-MM-dd") Date writeDate;
    private String cardAnswer;
}
