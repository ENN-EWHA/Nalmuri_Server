package com.example.nalmuri.controller;

import com.example.nalmuri.DTO.CardDTO;
import com.example.nalmuri.DTO.board.AnswerDTO;
import com.example.nalmuri.DTO.board.DiaryDTO;
import com.example.nalmuri.DTO.board.WriteDTO;
import com.example.nalmuri.DTO.request.EmotionRequestDTO;
import com.example.nalmuri.DTO.response.CustomedCardResponseDTO;
import com.example.nalmuri.DTO.response.EmotionResponseDTO;
import com.example.nalmuri.entity.Board;
import com.example.nalmuri.entity.Card;
import com.example.nalmuri.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    //다이어리 작성
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void writeDiary(@RequestBody WriteDTO writeDTO) throws Exception{
        boardService.writeDiary(writeDTO.getWriteDate(), writeDTO.getUserid(), writeDTO.getDiary());
    }

    // 전체 다이어리 조회
    @GetMapping("/{userid}")
    public List<Board> getAllDiary(@PathVariable String userid) throws Exception{
        List<Board> boardList= boardService.getAllDiary(userid);
        return boardList;
    }

    //전체 질문카드 조회
    @GetMapping("/question/{userid}")
    public List<CustomedCardResponseDTO> getAllCard(@PathVariable String userid) throws Exception{
        List<Board> boardList = boardService.getAllDiary(userid);
        log.info(boardList.toString());
        List<Integer> cardidList = boardList.stream().map(h->h.getCardid()).collect(Collectors.toList());
        log.info(cardidList.toString());
        List<Card> cardList = boardService.getAllCard(cardidList);
        return boardService.getCustomedCardList(cardList,boardList);

    }

    //감정별 질문카드 조회
    @GetMapping("/question/{userid}/list/{emotion}")
    public List<CustomedCardResponseDTO> getEmotionCard(@PathVariable String userid, @PathVariable int emotion){
        List<Board> boardList = boardService.getAllDiary(userid);
        List<Integer> cardidList = boardList.stream().map(h->h.getCardid()).collect(Collectors.toList());
        List<Card> cardList = boardService.getAllCard(cardidList);
        List<CustomedCardResponseDTO> list = boardService.getCustomedCardList(cardList,boardList);
        list.stream().filter(h->h.getEmotion()==emotion).collect(Collectors.toList());
        return list;
    }
    //감정 분석 요청
    @GetMapping("/question/nlp")
    public String getDiaryEmotion(@RequestBody EmotionRequestDTO request) throws Exception{

        String url = "http://34.64.209.5:5000/api";
        String jsonInString = "";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpEntity entity = new HttpEntity(request, headers);


        RestTemplate template = new RestTemplate();
        ResponseEntity<EmotionResponseDTO> responseEntity = restTemplate.exchange("http://34.64.209.5:5000/api", HttpMethod.POST, entity, EmotionResponseDTO.class);

        log.info(String.valueOf(responseEntity.getBody()));

        ObjectMapper mapper = new ObjectMapper();
//        EmotionResponseDTO response = obj.readValue((DataInput) responseEntity.getBody(), EmotionResponseDTO.class);
//        response.setEmotion(String.valueOf(responseEntity.getBody()));
        jsonInString = mapper.writeValueAsString(responseEntity.getBody());
        return jsonInString;

    }

    //특정 날짜 다이어리 조회
    @GetMapping
    public String getDailyDiary(@RequestBody DiaryDTO diaryDTO) throws Exception{
        Board board = boardService.getDailyBoard(diaryDTO);
        return board.getDiary();
    }

    //다이어리 내용 삭제
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteDiary(@RequestBody DiaryDTO diaryDTO) throws Exception{
        boardService.deleteDiary(diaryDTO);
    }

//    @GetMapping("/question")
//    public String getQuestion(@RequestBody BoardDTO boardDTO) throws Exception{
//        boardService.getQuestion(boardDTO){

//
//import com.example.nalmuri.DTO.BoardDTO;
//import com.example.nalmuri.DTO.DiaryDTO;
//import com.example.nalmuri.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/board")
//public class BoardController {
//    private final BoardService boardService;
//
//    //다이어리 작성
//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public void writeDiary(@RequestBody DiaryDTO diaryDTO, String diary) throws Exception{
//        boardService.writeDiary(diaryDTO, diary);
//    }

//
//    //특정 날짜 다이어리 조회
//    @GetMapping
//    public String getDailyDiary(@RequestBody DiaryDTO diaryDTO) throws Exception{
//        return boardService.getDailyDiary(diaryDTO);
//    }
//
//    //다이어리 내용 삭제
//    @DeleteMapping
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteDiary(@RequestBody DiaryDTO diaryDTO) throws Exception{
//        boardService.deleteDiary(diaryDTO);
//    }
//
////    @GetMapping("/question")
////    public String getQuestion(@RequestBody BoardDTO boardDTO) throws Exception{
////        boardService.getQuestion(boardDTO){
////
////        }
////    }
//
//    @PostMapping("/question")
//    @ResponseStatus(HttpStatus.OK)
//    public void writeAnswer(@RequestBody DiaryDTO diaryDTO, String answer) throws Exception{
//        boardService.writeAnswer(diaryDTO, answer);
//    }
//
//    @GetMapping("/question")
//    public String getDailyAnswer(@RequestBody DiaryDTO diaryDTO) throws Exception{
//        return boardService.getDailyAnswer(diaryDTO);
//    }
//
//    @DeleteMapping("/question")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteAnswer(@RequestBody DiaryDTO diaryDTO) throws Exception{
//        boardService.deleteAnswer(diaryDTO);
//    }
//}


    @PostMapping("/card")
    @ResponseStatus(HttpStatus.OK)
    public void writeAnswer(@RequestBody AnswerDTO answerDTO) throws Exception{
        boardService.writeAnswer(answerDTO);
    }

    @GetMapping("/card")
    public String getDailyAnswer(@RequestBody DiaryDTO diaryDTO) throws Exception{
        Board board = boardService.getDailyBoard(diaryDTO);
        return board.getCardAnswer();
    }

    @GetMapping("/card/request/{emotion}")
    public Card getCardQuestion(@PathVariable int emotion){
        return boardService.getCardQuestion(emotion);
    }

}

