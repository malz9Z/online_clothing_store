package com.thungashoe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

	@GetMapping("/login")
    public String login(){
        return "shop/login";
    }
	
	@GetMapping("/register")
	public String register(Model model) {		
		return "shop/register";
	}
	
	@GetMapping("/my-info")
	public String myInfo(Model model) {		
		return "shop/my-info";
	}
	
	@GetMapping("/my-wishlist")
	public String myWishlist(Model model) {		
		return "shop/my-wishlist";
	}
	
	@GetMapping("/my-order")
	public String myOrder(Model model) {		
		return "shop/my-order";
	}
}
