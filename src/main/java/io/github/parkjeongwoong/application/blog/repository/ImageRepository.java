package io.github.parkjeongwoong.application.blog.repository;

import io.github.parkjeongwoong.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
