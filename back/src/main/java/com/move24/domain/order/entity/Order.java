package com.move24.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.move24.common.entity.DateEntity;
import com.move24.domain.member.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.move24.domain.order.entity.OrderStatus.WAITING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderer_id")
    private Member orderer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Member driver;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String departureAddress;
    private String arrivalAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDate expectedDate;

    private String content;

    @Builder
    private Order(Member orderer, Member driver, String departureAddress, String arrivalAddress, LocalDate expectedDate, String content) {
        this.orderer = orderer;
        this.driver = driver;
        this.status = WAITING;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.expectedDate = expectedDate;
        this.content = content;
    }
}
