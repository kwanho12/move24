package com.move24.domain.review.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewStatus {
    POSTED("게시"), DELETED("삭제");

    private final String text;
}
