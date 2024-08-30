package com.move24.domain.order.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    APPROVAL("승인"), WAITING("대기"), REJECTION("거절");

    private final String text;
}
