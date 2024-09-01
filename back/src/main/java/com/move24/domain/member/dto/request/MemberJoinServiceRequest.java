package com.move24.domain.member.dto.request;

import com.move24.common.exception.BusinessPolicyValidErrorResponse;
import com.move24.common.exception.BusinessPolicyValidationException;
import lombok.Builder;
import lombok.Getter;

import static com.move24.common.utils.ValidationUtil.*;

@Getter
public class MemberJoinServiceRequest {

    private final String userId;
    private final String password;
    private final String name;
    private final String gender;
    private final String mail;
    private final String address;
    private final String phoneNumber;
    private final String role;

    @Builder
    private MemberJoinServiceRequest(String userId, String password, String name, String gender, String mail, String address, String phoneNumber, String role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void validate() {
        validate("userId", validateUserId(userId));
        validate("password", validatePassword(password));
        validate("name", validateName(name));
        validate("email", validateEmail(mail));
        validate("address", validateAddress(address));
        validate("phoneNumber", validatePhoneNumber(phoneNumber));
    }

    private void validate(String field, String message) {
        if (message != null) {
            BusinessPolicyValidErrorResponse businessPolicyValidErrorResponse =
                    BusinessPolicyValidErrorResponse.builder()
                    .field(field)
                    .message(message)
                    .build();
            throw new BusinessPolicyValidationException(
                    businessPolicyValidErrorResponse.getMessage(), businessPolicyValidErrorResponse
            );
        }
    }
}
