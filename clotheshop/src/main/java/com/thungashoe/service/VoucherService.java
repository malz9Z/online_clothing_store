package com.thungashoe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thungashoe.entity.Voucher;
import com.thungashoe.repository.VoucherRepository;

@Service
public class VoucherService {

	@Autowired
	private VoucherRepository voucherRepository;
	
	public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
	
	public Voucher getVoucherById(Long voucherId) {
		return voucherRepository.findById(voucherId).orElse(null);
    }
	
	public void addVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
	
	public void updateVoucher(Long voucherId, Voucher updatedVoucher) {
        Voucher existingVoucher = getVoucherById(voucherId);
        if (existingVoucher != null) {
        	updatedVoucher.setId(voucherId);
            voucherRepository.save(updatedVoucher);
        }
    }
}
