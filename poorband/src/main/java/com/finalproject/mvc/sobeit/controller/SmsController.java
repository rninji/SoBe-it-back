package com.finalproject.mvc.sobeit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.mvc.sobeit.dto.MessageDTO;
import com.finalproject.mvc.sobeit.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 회원가입 시 SMS 인증할 때 사용되는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;
    private final HttpSession session;

    @PostMapping("sms/smsAuthRequset")
    public Boolean phoneAuth(String tel) throws JSONException, UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

//        try { // 이미 가입된 전화번호가 있으면
//            if(memberService.memberTelCount(tel) > 0)
//                return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        MessageDTO messageDTO = new MessageDTO();
        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 6; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        messageDTO.setContent("Sobe-it 인증번호는 [" + numStr + "] 입니다.");
        messageDTO.setTo(tel);
        smsService.sendSms(messageDTO);
        session.setAttribute("rand", numStr);

        return true;
    }

    @PostMapping("sms/smsAuthOk")
    public Boolean phoneAuthOk(@RequestParam String code) {
        String savedAuthCode = (String) session.getAttribute("rand");

        System.out.println(savedAuthCode + " : " + code);

        if (savedAuthCode != null && savedAuthCode.equals(code)) {
            session.removeAttribute("rand");
            return true;  // Auth successful
        }

        return false;  // Auth failed
    }
}
