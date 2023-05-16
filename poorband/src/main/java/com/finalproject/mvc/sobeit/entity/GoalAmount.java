package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoalAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goal_amount_seq;
    @Column(nullable = false)
    private Long goal_amount;
    @ManyToOne
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private Users user_seq;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

    @Column(nullable = false, length = 4)
    private String is_success;

}
