package com.move24.domain.review.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewLikeStatus {
    ACTIVE("활성화"), CANCELED("취소");

    private final String text;
}
