package com.move24.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_relation_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;

}
