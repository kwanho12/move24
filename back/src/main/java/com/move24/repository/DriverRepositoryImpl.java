package com.move24.repository;

import com.move24.enums.Gender;
import com.move24.request.DriverSearchCondition;
import com.move24.response.DriverOneResponse;
import com.move24.response.DriversResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.move24.domain.QDriver.*;
import static com.move24.domain.QImage.*;
import static com.move24.domain.QMember.*;

public class DriverRepositoryImpl implements DriverRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DriverRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<DriverOneResponse> getDriverOne(String driverId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.fields(DriverOneResponse.class,
                        member.userId.as("driverId"),
                        member.details.name,
                        member.details.gender,
                        member.details.mail,
                        member.details.phoneNumber,
                        driver.experienceYear,
                        driver.content,
                        driver.likeCount,
                        image.fileName))
                .from(driver)
                .join(driver.member, member)
                .join(member.image, image)
                .where(member.userId.eq(driverId))
                .fetchOne());
    }

    @Override
    public Page<DriversResponse> getDrivers(DriverSearchCondition condition, Pageable pageable) {
        List<DriversResponse> content = queryFactory
                .select(Projections.fields(DriversResponse.class,
                        member.details.name,
                        driver.experienceYear,
                        driver.likeCount,
                        image.fileName))
                .from(driver)
                .join(driver.member, member)
                .join(member.image, image)
                .where(nameContains(condition.getName()),
                        genderEq(condition.getGender()),
                        mailContains(condition.getMail()),
                        phoneNumberContains(condition.getPhoneNumber()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(driver)
                .join(driver.member, member)
                .where(nameContains(condition.getName()),
                        genderEq(condition.getGender()),
                        mailContains(condition.getMail()),
                        phoneNumberContains(condition.getPhoneNumber())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression nameContains(String nameCond) {
        return nameCond != null ? member.details.name.contains(nameCond) : null;
    }

    private BooleanExpression genderEq(Gender genderCond) {
        return genderCond != null ? member.details.gender.eq(genderCond) : null;
    }

    private BooleanExpression mailContains(String mailCond) {
        return mailCond != null ? member.details.mail.contains(mailCond) : null;
    }

    private BooleanExpression phoneNumberContains(String phoneNumberCond) {
        return phoneNumberCond != null ? member.details.phoneNumber.contains(phoneNumberCond) : null;
    }
}
