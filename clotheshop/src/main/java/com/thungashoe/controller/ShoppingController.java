package com.thungashoe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thungashoe.exception.ExistedCartItemException;

@Controller
public class ShoppingController {

	@ExceptionHandler(ExistedCartItemException.class)
	public ResponseEntity<String> handleInsufficientStockException(ExistedCartItemException exc) {
		return ResponseEntity.internalServerError().body("Not enough stock!!!");
	}
}
