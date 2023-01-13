package com.example.nalmuri.service;


import com.example.nalmuri.DTO.request.IdRequestDTO;
import com.example.nalmuri.DTO.request.PwUpdateRequestDTO;
import com.example.nalmuri.DTO.request.UserUpdateDTO;
import com.example.nalmuri.DTO.response.MemberResponseDTO;
import com.example.nalmuri.config.SecurityUtil;
import com.example.nalmuri.entity.User;
import com.example.nalmuri.exception.ApiRequestException;
import com.example.nalmuri.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "maroony55@gmail.com";
    private final PasswordEncoder passwordEncoder;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date foundbirth;


    public MemberResponseDTO getMyInfo() {
        return userRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new ApiRequestException("로그인 유저 정보가 없습니다"));
    }

    public MemberResponseDTO getInfo(String userid){
        return userRepository.findById(userid)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new ApiRequestException("회원이 없습니다."));
    }

    public List<MemberResponseDTO> getUsers(){
        List<User> userList = userRepository.findAll();

        if(userList==null || userList.isEmpty()){
            throw new ApiRequestException("no users");
        }
        List<MemberResponseDTO> filteredList = userList.stream().map(MemberResponseDTO::of).collect(Collectors.toList());
        return filteredList;
    }

    @Transactional
    //회원 정보 수정
    public void updateUserInfo(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()-> new ApiRequestException("로그인 상태가 아닙니다."));
        user.updateUserInfo(userUpdateDTO.getEmail(), userUpdateDTO.getBirth());

    }


    //비밀번호 수정
    @Transactional
    public MemberResponseDTO changePassword(PwUpdateRequestDTO pwUpdateRequestDTO) throws Exception {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new ApiRequestException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(pwUpdateRequestDTO.getCurrentPw(), user.getUserpw())) {
            throw new ApiRequestException("비밀번호가 맞지 않습니다");
        }
        if(!pwUpdateRequestDTO.getNewPw().equals(pwUpdateRequestDTO.getNewPasswordCheck())){
            throw new ApiRequestException("새로운 비밀번호가 일치하지 않습니다");
        }
        user.updatePassword(passwordEncoder.encode(pwUpdateRequestDTO.getNewPw()));
        return MemberResponseDTO.of(userRepository.save(user));
    }


    //회원탈퇴
    @Transactional
    public void withdraw(String checkPassword) throws Exception {
        User user = userRepository.findByUserid(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new ApiRequestException("회원이 존재하지 않습니다."));

        if(!user.matchPassword(passwordEncoder, checkPassword)){
            throw new ApiRequestException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.delete(user);


    }

    public String findId(IdRequestDTO idRequestDTO){
        Optional<User> user = userRepository.findByEmail(idRequestDTO.getEmail());
        log.info(String.valueOf(user.get().getBirth()));

        if(!user.isPresent()){
            throw new ApiRequestException("가입되지 않은 이메일입니다.");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(user.get().getBirth());
        log.info(date);

        String date2 = format.format(idRequestDTO.getBirth());
        log.info(date2);

        if(!date.equals(date2)){
            throw new ApiRequestException("생년월일이 일치하지 않습니다.");

        }
        return user.get().getUserid();
    }



    public boolean userEmailcheck(String email, String userid){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()&&user.get().getUserid().equals(userid)){
            return true;
        }
        else{
            return false;
        }

    }




}
