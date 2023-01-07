package com.example.nalmuri.repository;


import com.example.nalmuri.DTO.board.DiaryDTO;
import com.example.nalmuri.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, DiaryDTO> {
    Board findByWriteDateAndUserid(Date writeDate, String userid);

    List<Board> findByUserid(String userid);
    boolean existsByWriteDate(Date writeDate);
}

