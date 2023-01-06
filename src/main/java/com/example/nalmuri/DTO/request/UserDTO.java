package com.example.nalmuri.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private String userpw;
    private String userpwConfirm;

    public UserDTO(String userpw, String userpwConfirm){
        if(userpw == null){

        }
        if(userpw.isEmpty()){

        }
        if(!(userpw.equals(userpwConfirm))){

        }
        this.userpw = userpw;

    }
}