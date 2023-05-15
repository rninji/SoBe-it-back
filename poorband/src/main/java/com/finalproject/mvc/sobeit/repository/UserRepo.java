package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Users findByUser_id(String user_id);
    Users findByEmail(String email);
    Boolean existsByUser_id(String user_id);
    Boolean existsByEmail(String email);
    Boolean existsByPhone_number(String phone_number);

    @Query(value = "select u from Users u where u.user_id like %?1%")
    List<Users> findAllByUser_id(String userId);

}
