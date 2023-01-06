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

    public List<CustomedCardResponseDTO> getCustomedCardList(List<Card> cardList, List<Board> boardList){

        if (boardList==null || boardList.isEmpty()){
            throw new ApiRequestException("작성된 일기 존재하지 않습니다.");
        }
        log.info(String.valueOf(cardList));

        List<Integer> cardidList = new ArrayList<Integer>();
        cardidList = cardList.stream().map(h->h.getCardid()).collect(Collectors.toList());
        log.info(String.valueOf(cardidList));
        for(Card card : cardList){
            try{
                int id = card.getCardid();
            }
            catch(NullPointerException e){
                throw new ApiRequestException("cardid가 존재하지 않습니다.");
            }
            if(card.getCardid()==-1){
                continue;
            }
            cardidList.add(card.getCardid());

        }

        if(cardidList==null || cardidList.isEmpty()){
            throw new ApiRequestException("작성된 일기에 대해 작성된 카드가 존재하지 않습니다.");
        }
        List<CustomedCardResponseDTO> joined = new ArrayList<CustomedCardResponseDTO>();
        for(Card card : cardList){
            for(Board board : boardList){
                if(board.getCardid()== card.getCardid()) {
                    CustomedCardResponseDTO newcard = new CustomedCardResponseDTO(board.getCardid(), board.getCardAnswer(), card.getCardquestion(), card.getEmotion());
                    joined.add(newcard);
                }
//                else{
//                    throw new ApiRequestException("몰랑");
//                }
            }
        }

        return joined;
    }

    //전체 일기 조회
    //전체 일기 조회
    public List<Board> getAllDiary(String userid){
        List<Board> boardList = boardRepository.findByUserid(userid);
        if(boardList==null || boardList.isEmpty()){
            throw new ApiRequestException("작성된 일기가 없습니다.");
        }
        return boardList;
//
    }

    //특정 날짜 일기 조회
    public Board getDailyBoard(DiaryDTO diaryDTO) throws Exception {
        return boardRepository.findByWriteDateAndUserid(diaryDTO.getWriteDate(), diaryDTO.getUserid()).orElseThrow(() -> new Exception("해당 날짜에 작성된 일기가 없습니다."));
    }

    public void deleteDiary(DiaryDTO diaryDTO) throws Exception {
        Board board = getDailyBoard(diaryDTO);
        boardRepository.delete(board);
    }

    //보드(일기, 감정 질문 카드, 대답 같이 있음)
    //전체 질문카드 조회
    public List<Card> getAllCard(List<Integer> cardidList){
        List<Card> allCards = new ArrayList<Card>();
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

