package com.move24.domain.member.entity.image;

import com.move24.domain.member.exception.ExtensionNotMatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExtensionTest {

    @DisplayName("올바른 확장자가 아니라면 예외가 발생한다.")
    @Test
    void isValidExtension() {
        // when, then
        assertThatThrownBy(() -> Extension.isValidExtension("pdf"))
                .isInstanceOf(ExtensionNotMatchException.class)
                .hasMessage("pdf은(는) 이미지 확장자가 아닙니다.");
    }
}