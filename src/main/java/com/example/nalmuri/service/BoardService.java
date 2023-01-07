package com.example.nalmuri.service;

import com.example.nalmuri.DTO.CardDTO;
import com.example.nalmuri.DTO.board.AnswerDTO;
import com.example.nalmuri.DTO.board.DiaryDTO;
import com.example.nalmuri.DTO.response.CustomedCardResponseDTO;
import com.example.nalmuri.entity.Board;
import com.example.nalmuri.entity.Card;
import com.example.nalmuri.exception.ApiRequestException;
import com.example.nalmuri.repository.BoardRepository;
import com.example.nalmuri.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    public void writeDiary(Date writeDate, String userid, String diary){
        Board board = new Board();
        board.setDiary(diary);
        board.setWriteDate(writeDate);
        board.setUserid(userid);
        boardRepository.save(board);
    }

    public List<CustomedCardResponseDTO> getCustomedCardList(List<Integer> cardidList, List<Board> boardList){



        List<Card> cardList = cardidList.stream().map(h->cardRepository.findByCardid(h)).collect(Collectors.toList());


        List<CustomedCardResponseDTO> cards = new ArrayList<CustomedCardResponseDTO>();
        for(int i=0;i<boardList.size();i++){
            Board diary = boardList.get(i);
            log.info(String.valueOf(diary));

            if(cardidList.get(i).equals(-1)){
                continue;
            }
            Card card = cardRepository.findByCardid(cardidList.get(i));
            log.info(String.valueOf(card));

            cards.add(new CustomedCardResponseDTO(diary.getCardid(), diary.getCardAnswer(), card.getCardquestion(), card.getEmotion()));

        }

        return cards;
    }


    //전체 일기 조회
    public List<Board> getAllDiary(String userid){
        List<Board> boardList = boardRepository.findByUserid(userid);
        if(boardList==null || boardList.isEmpty()){
            throw new ApiRequestException("작성된 일기가 없습니다.");
        }
        return boardList;
    }

    //특정 날짜 일기 조회
    public Board getDailyBoard(DiaryDTO diaryDTO) {
        if (!boardRepository.existsByWriteDate(diaryDTO.getWriteDate())){
            throw new ApiRequestException("해당 날짜에 작성된 일기가 없습니다.");
        }
        return boardRepository.findByWriteDateAndUserid(diaryDTO.getWriteDate(), diaryDTO.getUserid());
    }

    public void deleteDiary(DiaryDTO diaryDTO) throws Exception {
        Board board = getDailyBoard(diaryDTO);
        boardRepository.delete(board);
    }

    //보드(일기, 감정 질문 카드, 대답 같이 있음)
    //전체 질문카드 조회
    public List<Card> getAllCard(List<Integer> cardidList){
        List<Card> allCards = new ArrayList<Card>();
        int count = 0;
        for(int i: cardidList){
            if(i==-1){
                count++;
            }
        }

        if(count==cardidList.size()){
            throw new ApiRequestException("작성된 카드가 없음");
        }

        cardidList.forEach(s -> allCards.add(cardRepository.findByCardid(s)));
        if(allCards==null || allCards.isEmpty()){
            throw new ApiRequestException("해당하는 카드가 없습니다.");
        }
        return allCards;
    }



    //질문카드 답변 작성
    public void writeAnswer(AnswerDTO answerDTO) throws Exception {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setWriteDate(answerDTO.getWriteDate());
        diaryDTO.setUserid(answerDTO.getUserid());
        Board board = getDailyBoard(diaryDTO);
        board.setCardAnswer(answerDTO.getCardAnswer());
        boardRepository.save(board);
    }

    public Card getCardQuestion(int emotion) {
        List<Card> cardList = cardRepository.findByEmotion(emotion);
        Random random = new Random();
        return cardList.get(random.nextInt(cardList.size()));

    }


}

