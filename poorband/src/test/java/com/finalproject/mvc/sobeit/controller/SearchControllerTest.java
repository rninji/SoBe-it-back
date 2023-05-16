package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WebAppConfiguration
class SearchControllerTest {

    private UserRepo userRepo;

    @Test
    void usersSearch() {
        List<Users> list = userRepo.findAllByUserId("1");
        list.forEach(b->System.out.println(b));
    }
}