package com.move24.domain.member.entity.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ROLE_USER("사용자(고객)"), ROLE_DRIVER("기사");

    private final String text;
}
