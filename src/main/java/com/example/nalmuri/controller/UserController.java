package com.example.nalmuri.controller;

import com.example.nalmuri.DTO.MailDTO;
import com.example.nalmuri.DTO.request.*;
import com.example.nalmuri.DTO.response.MemberResponseDTO;
import com.example.nalmuri.Message;
import com.example.nalmuri.service.MailService;
import com.example.nalmuri.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailservice;


    //delete user
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@RequestBody UserWithdrawDTO userWithdrawDTO) throws  Exception{

        userService.withdraw(userWithdrawDTO.getCheckPassword());


    }



    //update user information(except id and password)
    @PutMapping("/my/info")
    public ResponseEntity updateUserInfo(@JsonFormat(pattern = "yyyy-MM-dd") @RequestBody UserUpdateDTO userUpdateDTO) throws  Exception{
        userService.updateUserInfo(userUpdateDTO);
        return new ResponseEntity<>(userUpdateDTO, HttpStatus.OK);
    }

    //update password
    @PutMapping("/my/info/pw")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Message> updatePassword(@RequestBody PwUpdateRequestDTO pwUpdateRequestDTO) throws  Exception{
        //dto 수정+ 서비스단 파라미터 dto로 수정 + 변수명 다를수도 있음확인
        userService.changePassword(pwUpdateRequestDTO);
        Message msg = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        msg.setMessage("새로운 비밀번호는 "+pwUpdateRequestDTO.getNewPw()+"입니다.");
        msg.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(msg,headers,HttpStatus.OK);
    }

    //전체 회원 조회
    @GetMapping("/list")
    public List<MemberResponseDTO> getUsers() {
        return userService.getUsers();
    }


    //get my info
    @GetMapping("/my/info")
    public ResponseEntity getMyInfo() throws  Exception{
        MemberResponseDTO info = userService.getMyInfo();
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    //get certain user's info
    @GetMapping("/info/{userid}")
    public ResponseEntity getInfo(@PathVariable String userid) throws Exception{
        MemberResponseDTO info = userService.getInfo(userid);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    //find id
    @GetMapping("/my/find/id")
    public ResponseEntity findId(@RequestBody IdRequestDTO idRequestDTO){
        String foundId = userService.findId(idRequestDTO);
        log.info(String.valueOf(idRequestDTO.getBirth()));
        return new ResponseEntity<>(foundId,HttpStatus.OK);
    }

      //find password
    @GetMapping("/my/find/password")
    @Transactional
    public ResponseEntity<Message> findPw(@RequestBody PwFindRequestDTO pwFindRequestDTO) throws Exception {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        boolean pwFindCheck = userService.userEmailcheck(pwFindRequestDTO.getEmail(), pwFindRequestDTO.getUserid());
        if(!pwFindCheck){
            message.setMessage("가입되지 않은 유저아이디와 이메일입니다.");
            message.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(message, headers,HttpStatus.BAD_REQUEST);
        }
        MailDTO mailDTO = mailservice.createMailAndChangePw(pwFindRequestDTO.getEmail());
        log.info(String.valueOf(mailDTO));
        mailservice.mailSend(mailDTO);
        message.setMessage("임시 비밀번호를 가입된 이메일로 전송했습니다.");
        message.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(message, headers,HttpStatus.OK);

    }





}
