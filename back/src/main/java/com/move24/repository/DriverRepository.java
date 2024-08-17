package com.move24.repository;

import com.move24.domain.Driver;
import com.move24.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, String>, DriverRepositoryCustom {

    Optional<Driver> findByMember(Member member);
}
