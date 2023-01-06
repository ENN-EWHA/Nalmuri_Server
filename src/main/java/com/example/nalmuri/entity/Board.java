package com.example.nalmuri.entity;

import com.example.nalmuri.DTO.board.DiaryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "board")
@IdClass(DiaryDTO.class)
public class Board {
    @Id
    @Column(name = "userid")
    private String userid;

    @Id
    private @JsonFormat(pattern = "yyyy-MM-dd")Date writeDate;

    @Column(name = "diary")
    private String diary;

    @Column(name = "cardid")
    private int cardid = -1;

    @Column(name = "cardanswer")
    private String cardAnswer = "null";

    @Builder
    public Board(String userid, Date writeDate, String diary, int cardid, String cardAnswer){
        this.userid = userid;
        this.writeDate = writeDate;
        this.diary = diary;
        this.cardid = cardid;
        this.cardAnswer = cardAnswer;
    }

}

