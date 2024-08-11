package com.move24.domain;

import com.move24.enums.Gender;
import com.move24.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends DateEntity {

    @Id
    @Column(name = "member_id")
    private String id;
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Embedded MemberDetails details;

    private LocalDateTime loginDate;

}
