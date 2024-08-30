package com.move24.domain.driver.repository;

import com.move24.domain.driver.entity.Driver;
import com.move24.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, String>, DriverRepositoryCustom {

    Optional<Driver> findByMember(Member member);
}
