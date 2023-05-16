package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository <Vote, Long>{
}
