package com.example.nalmuri.DTO.request;


import com.example.nalmuri.exception.ApiRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PwUpdateRequestDTO {
    private String userid;
    private String currentPw;
    private String newPw;
    private String newPasswordCheck;

    public PwUpdateRequestDTO(String userid, String currentPw, String newPw, String newPasswordCheck){
        if(currentPw.isEmpty()){
            throw new ApiRequestException("현재 비밀번호를 입력해주세요.");
        }

        if(newPw.isEmpty() || newPasswordCheck.isEmpty()){
            throw new ApiRequestException("변경할 비밀번호를 입력해주세요.");
        }

        if(currentPw.equals(newPw)){
            throw new ApiRequestException("변경할비밀번호와 현재 비밀번호가 같습니다.");
        }

        if(newPw.length() < 4 || newPw.length() > 20){
            throw new ApiRequestException("변경할 비밀번호는  4~20자리를 사용해야 합니다.");
        }

        if ( !newPw.equals(newPasswordCheck)){
            throw new ApiRequestException("새로운 비밀번호와 비밀번호확인이 서로같지않습니다.");
        }
        this.currentPw = currentPw;
        this.newPw = newPw;
        this.newPasswordCheck = newPasswordCheck;
    }

    //    public void setNewPw(String newPw){
//        this.newPw = newPw;
//    }
}