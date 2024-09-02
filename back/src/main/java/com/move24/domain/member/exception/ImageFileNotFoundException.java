package com.move24.domain.member.exception;

import com.move24.common.exception.BusinessPolicyValidationException;

public class ImageFileNotFoundException extends BusinessPolicyValidationException {
   public ImageFileNotFoundException(String message, String field) {
       super(message, field);
   }
}
