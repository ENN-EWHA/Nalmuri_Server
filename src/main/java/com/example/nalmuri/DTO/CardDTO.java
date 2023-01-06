package com.example.nalmuri.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {
    private int cardid;
    private int emotion;
    private String cardquestion; //카드 질문 내용
}