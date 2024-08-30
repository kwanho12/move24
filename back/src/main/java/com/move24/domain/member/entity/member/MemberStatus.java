package com.move24.domain.member.entity.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE("활동중"), WITHDRAWAL("탈퇴");

    private final String text;
}
