package com.move24.domain.member.entity.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    MALE("남성"), FEMALE("여성");

    private final String text;
}
