package com.thungashoe.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.thungashoe.common.OrderAction;
import com.thungashoe.domain.dto.CartDTO;
import com.thungashoe.domain.dto.CartItemDTO;
import com.thungashoe.domain.dto.ShippingDTO;
import com.thungashoe.domain.entity.Order;
import com.thungashoe.domain.entity.OrderDetail;
import com.thungashoe.domain.entity.OrderTrack;
import com.thungashoe.domain.entity.ProductItem;
import com.thungashoe.domain.entity.User;
import com.thungashoe.repository.OrderDetailRepository;
import com.thungashoe.repository.OrderRepository;
import com.thungashoe.repository.OrderTrackRepository;
import com.thungashoe.repository.ProductItemRepository;
import com.thungashoe.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderTrackRepository orderTrackRepository;
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	public Page<Order> getAllOrders(int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return orderRepository.findAll(pageable);
	}
	
	public Order getOrderById(String id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public synchronized Order initOrder(CartDTO cart, Authentication authentication, ShippingDTO shipping) {
        List<CartItemDTO> cartItems = cart.getCartItems();
        
        Set<OrderDetail> orderDetails = cartItems.stream().map(cartItem -> {
        	ProductItem product = productItemRepository.findById(cartItem.getIdProductItem()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
            return OrderDetail.builder()
                    .product(product)
                    .price(cartItem.getPrice())
                    .qtySell(cartItem.getQuantity())
                    .build();
        }).collect(Collectors.toSet());
        
        Order order = Order.builder()
        		.customer((User) authentication.getPrincipal())
        		.fullName(shipping.fullName())
        		.phone(null)
        		.idAddress(null)
        		.addressDetail(null)
        		.priceShip(null)
        		.paymentMethod(null)
        		.voucher(null)
        		.build();
        order = orderRepository.save(order);
        
        OrderTrack orderTrack = OrderTrack.builder()
        		.order(order)
        		.action(OrderAction.INITIALIZATION)
        		.build();
        orderTrackRepository.save(orderTrack);
		return order;
    }
}
