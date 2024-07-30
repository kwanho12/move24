package com.move24.domain;

import com.move24.enums.ReviewLikeStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ReviewLike {

    @Id @GeneratedValue
    @Column(name = "review_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ReviewLikeStatus status;

    private LocalDateTime likeDate;

}
