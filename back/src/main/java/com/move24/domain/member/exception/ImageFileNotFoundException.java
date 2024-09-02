package com.move24.domain.member.exception;

import com.move24.common.exception.BusinessPolicyValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageFileNotFoundException extends BusinessPolicyValidationException {
   public ImageFileNotFoundException(String message, String field) {
       super(message, field);
   }
}
