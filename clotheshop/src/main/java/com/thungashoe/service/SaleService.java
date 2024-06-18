package com.thungashoe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thungashoe.entity.Sale;
import com.thungashoe.repository.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	
	public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
	
	public Sale getSaleById(Long saleId) {
		return saleRepository.findById(saleId).orElse(null);
    }
	
	public void addSale(Sale sale) {
        saleRepository.save(sale);
    }
	
	public void updateSale(Long saleId, Sale updatedSale) {
        Sale existingSale = getSaleById(saleId);
        if (existingSale != null) {
        	updatedSale.setId(saleId);
            saleRepository.save(updatedSale);
        }
    }
}
