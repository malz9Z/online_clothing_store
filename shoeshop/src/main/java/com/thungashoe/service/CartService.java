package com.thungashoe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thungashoe.dto.CartDTO;
import com.thungashoe.dto.CartItemDTO;
import com.thungashoe.exception.ExistedCartItemException;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

	@Autowired
    private HttpSession session;
	
	public CartDTO getCart() {
        CartDTO cart = (CartDTO) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartDTO();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
	
	public void addToCart(CartItemDTO cartItem) {
        CartDTO cart = getCart();
        List<CartItemDTO> cartItems = cart.getCartItems();

        // Kiểm tra xem mục đã tồn tại trong giỏ hàng chưa
        Optional<CartItemDTO> existingCartItem = cartItems.stream()
                .filter(item -> item.getIdProductItem().equals(cartItem.getIdProductItem()))
                .findFirst();
        if (existingCartItem.isPresent()) {
            // Cập nhật số lượng nếu mục đã tồn tại
        	Error error = new Error("Invalid cart");
        	throw new ExistedCartItemException(error);
        } else {
            // Thêm mới mục vào giỏ hàng
            cartItems.add(cartItem);
        }

        session.setAttribute("cart", cart);
    }
	
	public void updateCartItemQuantity(String productId, int quantity) {
        CartDTO cart = getCart();
        List<CartItemDTO> cartItems = cart.getCartItems();

        Optional<CartItemDTO> cartItemToUpdate = cartItems.stream()
                .filter(item -> item.getIdProductItem().equals(productId))
                .findFirst();

        cartItemToUpdate.ifPresent(item -> item.setQuantity(quantity));

        session.setAttribute("cart", cart);
    }
	
	public void removeCartItem(Long productId) {
        CartDTO cart = getCart();
        List<CartItemDTO> cartItems = cart.getCartItems();

        cartItems.removeIf(item -> item.getIdProductItem().equals(productId));

        session.setAttribute("cart", cart);
    }
	
	public void clearCart() {
        CartDTO cart = new CartDTO();
        session.setAttribute("cart", cart);
    }
}
