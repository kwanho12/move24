package com.move24.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.move24.domain.member.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.move24.domain.review.entity.ReviewLikeStatus.ACTIVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime likeDate;

    @Builder
    private ReviewLike(Review review, Member member, LocalDateTime likeDate) {
        this.review = review;
        this.member = member;
        this.status = ACTIVE;
        this.likeDate = likeDate;
    }
}
