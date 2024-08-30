package com.move24.domain.comment.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommentStatus {
    POSTED("게시"), DELETED("삭제");

    private final String text;
}
