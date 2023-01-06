package com.example.nalmuri.DTO.request;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonAutoDetect
public class EmotionRequestDTO {
    private String sentence;
}