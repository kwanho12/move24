package com.move24.domain.review.entity;

import com.move24.common.entity.DateEntity;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.member.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.move24.domain.review.entity.ReviewStatus.POSTED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends DateEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private String title;
    private String content;
    private int point;

    @Builder
    private Review(Member member, Driver driver, String title, String content, int point) {
        this.member = member;
        this.driver = driver;
        this.status = POSTED;
        this.title = title;
        this.content = content;
        this.point = point;
    }
}
