package com.move24.domain.driver.repository;

import com.move24.domain.driver.dto.request.DriverSearchServiceCondition;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import com.move24.domain.member.entity.member.Gender;
import com.querydsl.core.types.ExpressionUtils;
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

import static com.move24.domain.driver.entity.QDriver.driver;
import static com.move24.domain.member.entity.image.QImage.image;
import static com.move24.domain.member.entity.member.QMember.member;
import static com.move24.domain.review.entity.QReview.review;
import static com.querydsl.jpa.JPAExpressions.select;


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
                        image.fileName,
                        review.point.avg().as("averagePoint")))
                .from(driver)
                .leftJoin(driver.review, review)
                .join(driver.member, member)
                .join(member.image, image)
                .where(member.userId.eq(driverId))
                .fetchOne());
    }

    @Override
    public Page<DriversResponse> getDrivers(DriverSearchServiceCondition condition, Pageable pageable) {

        List<DriversResponse> content = queryFactory
                .select(Projections.fields(DriversResponse.class,
                        member.userId.as("driverId"),
                        member.details.name,
                        driver.experienceYear,
                        image.fileName,
                        ExpressionUtils.as(
                                select(review.point.avg()).from(review).groupBy(review.driver),
                                "averagePoint")
                        )
                )
                .from(driver)
                .leftJoin(driver.review, review)
                .join(driver.member, member)
                .join(member.image, image)
                .where(nameContains(condition.getName()),
                        genderEq(condition.getGender()),
                        mailContains(condition.getMail()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        content.forEach(driversResponse -> {
            if (driversResponse.getAveragePoint() == null) {
                driversResponse.setAveragePoint(0.0);
            }
        });

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
