package com.thungashoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.entity.OrderDetail;
import com.thungashoe.entity.composite_key.OrderDetailId;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId>{

}
