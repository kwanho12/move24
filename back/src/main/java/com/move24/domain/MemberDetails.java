package com.move24.domain;

import com.move24.enums.Gender;
import com.move24.enums.Role;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
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

}
