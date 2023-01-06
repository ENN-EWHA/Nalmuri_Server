package com.example.nalmuri.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
@Builder
@Table(name="card", uniqueConstraints = {@UniqueConstraint(columnNames = {"cardid"})})
public class Card {
    @Id
    private int cardid;

    @Column(nullable = false, name = "emotion")
    private int emotion;

    @Column(nullable = false, name = "cardquestion")
    private String cardquestion;


    @Builder
    public Card(int cardid, int emotion, String cardquestion) {
        this.cardid = cardid;
        this.emotion = emotion;
        this.cardquestion = cardquestion;
    }
}
