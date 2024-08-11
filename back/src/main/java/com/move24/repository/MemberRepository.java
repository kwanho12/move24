package com.move24.repository;

import com.move24.domain.Member;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @NotNull Optional<Member> findById(@NotNull String memberId);

    boolean existsById(@NotNull String id);
}
