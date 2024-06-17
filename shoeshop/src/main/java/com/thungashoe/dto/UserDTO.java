package com.thungashoe.dto;

public record UserDTO(
		String username,
		String email,
		String phone,
		String idAddress,
		String addressDetail,
		Boolean isDeleted) {

}
