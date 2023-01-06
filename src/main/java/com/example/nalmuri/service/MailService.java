package com.example.nalmuri.service;


import com.example.nalmuri.DTO.MailDTO;
import com.example.nalmuri.entity.User;
import com.example.nalmuri.exception.ApiRequestException;
import com.example.nalmuri.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    @Autowired
    private UserRepository userRepository;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "maroony55@gmail.com";

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MailDTO createMailAndChangePw(String email) throws Exception {
        String tempPw = getTempPw();
        MailDTO mailDTO = new MailDTO();
        mailDTO.setAddress(email);
        mailDTO.setTitle("날무리 임시비밀번호 안내 이메일입니다.");
        mailDTO.setMessage("안녕하세요. 날무리 임시 비밀번호 관련 이메일 드립니다. "+"회원님의 임시 비밀번호는 "+tempPw+"입니다."+"로그인 후에 비밀번호를 변경해주세요.");
        updateAsTempPw(tempPw, email);
        return mailDTO;
    }


    public void updateAsTempPw(String tempPw, String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ApiRequestException("가입되지 않은 이메일입니다."));
        user.updatePassword(passwordEncoder.encode((tempPw)));
    }

    public void mailSend(MailDTO mailDTO){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailDTO.getAddress());
        msg.setFrom(FROM_ADDRESS);
        msg.setSubject(mailDTO.getTitle());
        msg.setText(mailDTO.getMessage());
        log.info(String.valueOf(msg));
        mailSender.send(msg);
    }

    public String getTempPw(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
