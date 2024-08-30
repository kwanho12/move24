package com.move24.domain.comment.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommentLikeStatus {
    ACTIVE("추천"), CANCELED("추천 취소");

    private final String text;
}
