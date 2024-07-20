package com.move24.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommentRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment comment;

}
