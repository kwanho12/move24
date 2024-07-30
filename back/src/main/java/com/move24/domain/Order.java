package com.move24.domain;

import com.move24.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
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
    private LocalDate expectedDate;
    private String content;

}
