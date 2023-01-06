package com.example.nalmuri.DTO.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO implements Serializable {
    private String userid;
    private @JsonFormat(pattern = "yyyy-MM-dd")Date writeDate;
}
