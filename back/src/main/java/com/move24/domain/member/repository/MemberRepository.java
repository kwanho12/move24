package com.move24.domain.member.repository;

import com.move24.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
