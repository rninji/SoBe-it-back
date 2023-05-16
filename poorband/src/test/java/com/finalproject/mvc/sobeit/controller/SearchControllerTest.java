package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WebAppConfiguration
class SearchControllerTest {

    private UserRepo userRep;

    @Test
    void usersSearch() {
        try{
            List<Users> list = userRep.findAllByUserId("1");
        } catch (NullPointerException exception) {
            throw new RuntimeException("검색어와 일치하는 유저가 없습니다.");
        }


    }
}