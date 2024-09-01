package com.move24.domain.member.entity.member;

import com.move24.common.entity.DateEntity;
import com.move24.domain.member.dto.request.MemberJoinServiceRequest;
import com.move24.domain.member.entity.image.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.move24.domain.member.entity.member.MemberStatus.ACTIVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Embedded
    MemberDetails details;

    public static Member create(MemberJoinServiceRequest request, Image image) {
        MemberDetails memberDetails = MemberDetails.builder()
                .gender(Gender.valueOf(request.getGender()))
                .name(request.getName())
                .mail(request.getMail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(Role.valueOf(request.getRole()))
                .build();

        return Member.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .image(image)
                .details(memberDetails)
                .build();
    }

    @Builder
    private Member(String userId, String password, Image image, MemberDetails details) {
        this.userId = userId;
        this.password = password;
        this.image = image;
        this.status = ACTIVE;
        this.details = details;
    }
}
