package com.dkhang.shopapplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dkhang.shopapplication.dtos.OrderDTO;
import com.dkhang.shopapplication.responses.OrderResponse;
import com.dkhang.shopapplication.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	
	private final OrderService orderService;

	@PostMapping("")
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
		return ResponseEntity.ok(orderService.createOrder(orderDTO));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> retrieveOrderByIdWithResponseFormat(@PathVariable int id){
		return ResponseEntity.ok(orderService.retrieveOrderByIdWithResponseFormat(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderResponse> updateOrderById(@Valid @RequestBody OrderDTO orderDTO ,@PathVariable int id){
		return ResponseEntity.ok(orderService.updateOrderById(id,orderDTO));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOrderById(@PathVariable int id){
		orderService.deleteOrderById(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/user/{user_id}")
	public ResponseEntity<List<OrderResponse>> retrieveOrderByUserId(@PathVariable int user_id){
		return ResponseEntity.ok(orderService.retrieveOrderByUserId(user_id));
	}
	
}
