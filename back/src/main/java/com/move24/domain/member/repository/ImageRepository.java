package com.move24.domain.member.repository;

import com.move24.domain.member.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
