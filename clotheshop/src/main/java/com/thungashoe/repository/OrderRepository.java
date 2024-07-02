package com.thungashoe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

    Page<Order> findByStatus(String status, Pageable pageable);

}
