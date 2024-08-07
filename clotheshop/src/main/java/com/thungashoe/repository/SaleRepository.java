package com.thungashoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String>{

}
