package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class SmsController {

    private SmsService smsService;
    private final HttpSession session;

    @PostMapping("phoneAuth")
    public Boolean phoneAuth(String tel) throws JSONException {

        try { // 이미 가입된 전화번호가 있으면
            if(memberService.memberTelCount(tel) > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        String code = smsService.sendRandomMessage(tel);
        session.setAttribute("rand", code);

        return false;
    }

    @PostMapping("auth/smsAuthOk")
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
