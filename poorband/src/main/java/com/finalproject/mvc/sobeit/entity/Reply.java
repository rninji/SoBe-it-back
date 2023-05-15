package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reply_seq;
    @ManyToOne
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private Users user_seq;
    @ManyToOne
    @JoinColumn(name = "article_seq", referencedColumnName = "article_seq")
    private Article article_seq;
    @Column(nullable = false)
    private String reply_text;

    @Column(nullable = false)
    private Long parent_reply_seq;
    @Column(nullable = false)
    private LocalDateTime written_date;

    @PrePersist
    public void prePersist(){
        this.parent_reply_seq = this.parent_reply_seq == null ? 0L : this.parent_reply_seq;
    }

}
