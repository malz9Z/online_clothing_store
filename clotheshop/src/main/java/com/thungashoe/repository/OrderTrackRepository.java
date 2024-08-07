package com.thungashoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.OrderTrack;
import com.thungashoe.domain.entity.composite_key.OrderTrackId;

@Repository
public interface OrderTrackRepository extends JpaRepository<OrderTrack, OrderTrackId>{

}
