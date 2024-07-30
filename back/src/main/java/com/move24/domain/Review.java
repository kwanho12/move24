package com.move24.domain;

import com.move24.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Review extends DateEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Member driver;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private String title;
    private String content;
    private int point;

}
