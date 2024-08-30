package com.move24.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

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
}
