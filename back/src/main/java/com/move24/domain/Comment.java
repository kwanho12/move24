package com.move24.domain;

import com.move24.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    private String content;

}
