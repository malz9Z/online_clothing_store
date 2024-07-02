package com.thungashoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String>{

}