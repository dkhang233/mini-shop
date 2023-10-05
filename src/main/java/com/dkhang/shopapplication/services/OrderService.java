package com.dkhang.shopapplication.services;


import java.util.List;

import com.dkhang.shopapplication.dtos.OrderDTO;
import com.dkhang.shopapplication.responses.OrderResponse;

public interface OrderService {
	
	OrderResponse createOrder(OrderDTO orderDTO);
	OrderResponse retrieveOrderByIdWithResponseFormat(int id);
	List<OrderResponse> retrieveOrderByUserId(int user_id);
	OrderResponse  updateOrderById(int id,OrderDTO orderDTO);
	void deleteOrderById(int id);
}
