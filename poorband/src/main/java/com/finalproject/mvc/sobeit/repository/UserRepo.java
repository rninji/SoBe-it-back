package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUserId(String user_id);
  
    Users findByEmail(String email);
  
    Users findByUserIdAndPassword(String user_id, String password);
  
    Boolean existsByUserId(String user_id);
  
    Boolean existsByEmail(String email);
  
    Boolean existsByPhoneNumber(String phone_number);
  
    @Query(value = "select u from Users u where u.userId like %?1%")
    List<Users> findAllByUserId(String userId);
}
