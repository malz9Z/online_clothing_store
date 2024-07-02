package com.thungashoe.domain.entity;

import com.thungashoe.domain.entity.composite_key.OrderDetailId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(OrderDetailId.class)
@Table(name = "order_details")
public class OrderDetail {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private ProductItem product;
	
	@JoinColumn(name = "qty_sell")
	private Long qtySell;
	
	private Double price;
	
	@Column(name = "number_star")
	private Integer numberStar;
	
	private String recomment;
}
