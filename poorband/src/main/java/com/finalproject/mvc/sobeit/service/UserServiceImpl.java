package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public Users create(final Users users) {
        if(users == null || users.getUserId() == null || users.getEmail() == null || users.getPhoneNumber() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String userId = users.getUserId();
        final String email = users.getEmail();
        final String phoneNumber = users.getPhoneNumber();

        if(userRepo.existsByUserId(userId)) {
            log.warn("UserId already exists {}", userId);
            throw new RuntimeException("UserId already exists");
        }

        if(userRepo.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        if(userRepo.existsByPhoneNumber(phoneNumber)) {
            log.warn("PhoneNumber already exists {}", phoneNumber);
            throw new RuntimeException("PhoneNumber already exists");
        }

        return userRepo.save(users);
    }

    @Override
    public Users getByCredentials(final String user_id, final String password, final PasswordEncoder encoder) {
        final Users originalUser = userRepo.findByUserId(user_id);

        // matches 메서드를 이용해 패스워드가 같은지 확인
        if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }

    @Override
    public Users findUserId(String inputUserName, String inputPhoneNumber) {
        //TODO : userRepo 업뎃 되면 붙여주기
        Users users = userRepo.findByUserNameAndPhoneNumber(inputUserName, inputPhoneNumber);
        return users;
    }
}
