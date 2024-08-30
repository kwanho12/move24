package com.move24.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.move24.domain.member.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.move24.domain.comment.entity.CommentLikeStatus.ACTIVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CommentLikeStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime likeDate;

    @Builder
    private CommentLike(Comment comment, Member member, LocalDateTime likeDate) {
        this.comment = comment;
        this.member = member;
        this.status = ACTIVE;
        this.likeDate = likeDate;
    }
}
