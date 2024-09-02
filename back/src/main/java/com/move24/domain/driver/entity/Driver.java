package com.move24.domain.driver.entity;

import com.move24.common.entity.DateEntity;
import com.move24.domain.driver.dto.request.DriverPostServiceRequest;
import com.move24.domain.driver.exception.RoleNotValidException;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.Role;
import com.move24.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Driver extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int experienceYear;

    private String content;

    @OneToOne(mappedBy = "driver", fetch = FetchType.LAZY)
    private Review review;

    public void update(Integer experienceYear, String content) {
        this.experienceYear = experienceYear;
        this.content = content;
    }

    public static Driver create(Member member, DriverPostServiceRequest request) {
        if (member.getDetails().getRole() == Role.ROLE_USER) {
            throw new RoleNotValidException("기사만 게시글 작성이 가능합니다.");
        }
        return Driver.builder()
                .member(member)
                .experienceYear(request.getExperienceYear())
                .content(request.getContent())
                .build();
    }

    @Builder
    private Driver(Member member, int experienceYear, String content, Review review) {
        this.member = member;
        this.experienceYear = experienceYear;
        this.content = content;
        this.review = review;
    }
}
