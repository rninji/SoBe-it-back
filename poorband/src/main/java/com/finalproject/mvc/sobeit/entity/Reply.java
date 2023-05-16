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
    private Long replySeq;

    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq")
    private Users userSeq;

    @ManyToOne
    @JoinColumn(name = "articleSeq", referencedColumnName = "articleSeq")
    private Article articleSeq;

    @Column(nullable = false)
    private String replyText;

    @Column(nullable = false)
    private Long parentReplySeq;

    @Column(nullable = false)
    private LocalDateTime writtenDate;

    @PrePersist
    public void prePersist(){
        this.parentReplySeq = this.parentReplySeq == null ? 0L : this.parentReplySeq;
    }
}
