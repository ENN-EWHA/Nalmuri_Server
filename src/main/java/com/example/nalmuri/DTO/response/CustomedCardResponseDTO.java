package com.example.nalmuri.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomedCardResponseDTO {
    private int cardid;
    private String cardAnswer;
    private String cardquestion;
    private int emotion;

}
