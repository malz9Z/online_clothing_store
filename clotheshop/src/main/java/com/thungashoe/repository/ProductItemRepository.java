package com.thungashoe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>{

	List<ProductItem> findByProductIdAndColorId(String productId, Long colorId);
	
}
