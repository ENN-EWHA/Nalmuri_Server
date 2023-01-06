package com.example.nalmuri.repository;

import com.example.nalmuri.DTO.CardDTO;
import com.example.nalmuri.entity.Card;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, CardDTO> {

    Card findByCardid(int cardid);
    List<Card> findByEmotion(int emotion);

    //Optional<Card> findByCardid(int cardid);
}
