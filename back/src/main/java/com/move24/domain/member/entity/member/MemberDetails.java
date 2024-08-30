package com.move24.domain.member.entity.member;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetails {

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;

    private String mail;

    private String phoneNumber;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private MemberDetails(Gender gender, String name, String mail, String phoneNumber, String address, Role role) {
        this.gender = gender;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

}
