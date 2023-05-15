package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
<<<<<<< HEAD

    Users findByUserId(String user_id);
=======
    Users findByUser_id(String user_id);
>>>>>>> ee8ee274aa21e7185dae385fc49f7d35d5a6db53
    Users findByEmail(String email);
    Boolean existsByUser_id(String user_id);
    Boolean existsByEmail(String email);
    Boolean existsByPhone_number(String phone_number);
}
