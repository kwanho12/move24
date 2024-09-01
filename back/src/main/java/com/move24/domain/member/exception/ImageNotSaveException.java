package com.move24.domain.member.exception;

import com.move24.common.exception.CommonException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotSaveException extends CommonException {
    public ImageNotSaveException(String message) {
        super(message);
    }
}
