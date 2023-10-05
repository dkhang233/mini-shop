package com.dkhang.shopapplication.services;

import java.util.List;

import com.dkhang.shopapplication.dtos.OrderDetailDTO;
import com.dkhang.shopapplication.responses.OrderDetailResponse;

public interface OrderDetailService {
	public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO);
	public OrderDetailResponse retrieveOrderDetailByIdWithResponseFormat(int id);
	public OrderDetailResponse updateOrder(int id , OrderDetailDTO orderDetailDTO);
	public void deleteOrder(int id);
	public List<OrderDetailResponse> retrieveOrderDetailsByOrderId(int order_id);
}
