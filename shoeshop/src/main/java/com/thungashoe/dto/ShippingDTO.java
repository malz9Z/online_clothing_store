package com.thungashoe.dto;

import com.thungashoe.common.PaymentMethod;

public record ShippingDTO(
		String fullName,
		String phone,
		String idAddress,
		String addressDetail,
		Double priceShip,
		PaymentMethod paymentMethod,
		String voucher) {

}
