package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public Users create(final Users users) {
        if(users == null || users.getUser_id() == null || users.getEmail() == null || users.getPhone_number() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String userId = users.getUser_id();
        final String email = users.getEmail();
        final String phoneNumber = users.getPhone_number();

        if(userRepo.existsByUser_id(userId)) {
            log.warn("UserId already exists {}", userId);
            throw new RuntimeException("UserId already exists");
        }

        if(userRepo.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        if(userRepo.existsByEmail(phoneNumber)) {
            log.warn("PhoneNumber already exists {}", phoneNumber);
            throw new RuntimeException("PhoneNumber already exists");
        }

        return userRepo.save(users);
    }

    @Override
    public Users getByCredentials(String user_id, String password) {
        return userRepo.findByUser_idAndPassword(user_id, password);
    }
}
