package com.move24.domain.comment.entity;

import com.move24.common.entity.DateEntity;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.move24.domain.comment.entity.CommentStatus.POSTED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    private Comment(Member member, Review review, String content) {
        this.member = member;
        this.review = review;
        this.status = POSTED;
        this.content = content;
    }
}
