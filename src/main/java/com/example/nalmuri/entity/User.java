package com.example.nalmuri.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
@Builder
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"userid"})})

public class User {
    @Id
    private String userid;

    @Column(nullable = false, name = "userpw")
    private String userpw;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name = "birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @Column(nullable = false, name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void updatePassword(String userpw) {
        this.userpw = userpw;
    }

    public void updateUserInfo(String email,@JsonFormat(pattern = "yyyy-MM-dd") Date birth){
        this.email = email;
        this.birth = birth;
    }


    @Builder
    public User(String userid, String userpw, Date birth, String email, Authority authority) {
        this.userid = userid;
        this.userpw = userpw;
        this.birth = birth;
        this.email = email;
        this.authority = authority;
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getUserpw());
    }


}
