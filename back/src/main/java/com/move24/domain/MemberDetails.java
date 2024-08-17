package com.move24.domain;

import com.move24.enums.Gender;
import com.move24.enums.Role;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetails {

    @Builder
    public MemberDetails(Gender gender, String name, String mail, String phoneNumber, String address, Role role) {
        this.gender = gender;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;
    private String mail;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

}
