package com.dkhang.shopapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dkhang.shopapplication.dtos.OrderDetailDTO;
import com.dkhang.shopapplication.responses.OrderDetailResponse;
import com.dkhang.shopapplication.services.OrderDetailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orderdetails")
public class OrderDetailController {
	
	private final OrderDetailService orderDetailService;

	@PostMapping("")
	public ResponseEntity<OrderDetailResponse> insertOrder(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
		return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDetailResponse> retrieveOrderByIdWithResponseFormat(@PathVariable int id){
		return ResponseEntity.ok(orderDetailService.retrieveOrderDetailByIdWithResponseFormat(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderDetailResponse> updateOrderById(@Valid @RequestBody OrderDetailDTO orderDetailDTO,@PathVariable int id){
		return ResponseEntity.ok(orderDetailService.updateOrder(id, orderDetailDTO));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOrderById(@PathVariable int id){
		orderDetailService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/order/{order_id}")
	public ResponseEntity<Object> retrieveOrderByOrderId(@PathVariable int order_id){
		return ResponseEntity.ok(orderDetailService.retrieveOrderDetailsByOrderId(order_id));
	}
	
	
}
