package com.move24.domain.member.entity.image;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImageStatus {

    POSTED("게시 중"), DELETED("삭제됨");

    private final String text;
}
