package com.thungashoe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long>{

	Optional<Size> findByName(String name);
}
