package com.dkhang.shopapplication.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.dtos.OrderDetailDTO;
import com.dkhang.shopapplication.exceptionhandler.DataNotFoundException;
import com.dkhang.shopapplication.models.Order;
import com.dkhang.shopapplication.models.OrderDetail;
import com.dkhang.shopapplication.models.Product;
import com.dkhang.shopapplication.repositories.OrderDetailRepository;
import com.dkhang.shopapplication.repositories.OrderRepository;
import com.dkhang.shopapplication.repositories.ProductRepository;
import com.dkhang.shopapplication.responses.OrderDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

	private final OrderDetailRepository orderDetailRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	@Override
	public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) {
		 OrderDetail orderDetail = orderDetailRepository.save(orderDetailDTOMapToOrderDetail(orderDetailDTO, new OrderDetail()));
		 return orderDetailMapToOrderDetailResponse(orderDetail, new OrderDetailResponse());
	}

	@Override
	public OrderDetailResponse retrieveOrderDetailByIdWithResponseFormat(int id) {
		return orderDetailMapToOrderDetailResponse(retrieveOrderDetailById(id), new OrderDetailResponse());	
	}

	@Override
	public OrderDetailResponse updateOrder(int id, OrderDetailDTO orderDetailDTO) {
		OrderDetail orderDetail = retrieveOrderDetailById(id);
		orderDetail = orderDetailDTOMapToOrderDetail(orderDetailDTO, orderDetail);
		return orderDetailMapToOrderDetailResponse(orderDetailRepository.save(orderDetail), new OrderDetailResponse());
	}

	@Override
	public void deleteOrder(int id) {
		orderDetailRepository.deleteById(id);
	}

	@Override
	public List<OrderDetailResponse> retrieveOrderDetailsByOrderId(int order_id) {
		return orderDetailRepository.findByOrder(retrieveOrderById(order_id))
						.stream()
						.map(orderDetail -> orderDetailMapToOrderDetailResponse(orderDetail, new OrderDetailResponse())).toList();
	}
	
	private OrderDetail orderDetailDTOMapToOrderDetail(OrderDetailDTO orderDetailDTO , OrderDetail orderDetail) {
		modelMapper.typeMap(OrderDetailDTO.class, OrderDetail.class).addMappings(mapper -> mapper.skip(OrderDetail::setId));
		modelMapper.map(orderDetailDTO, orderDetail);
		orderDetail.setOrder(retrieveOrderById(orderDetailDTO.getOrderId()))
				.setProduct(retrieveProductById(orderDetailDTO.getProductId()));
		return orderDetail;
	}
	
	private OrderDetailResponse orderDetailMapToOrderDetailResponse( OrderDetail orderDetail , OrderDetailResponse orderDetailResponse) {
		modelMapper.typeMap(OrderDetail.class, OrderDetailResponse.class);
		modelMapper.map(orderDetail, orderDetailResponse);
		orderDetailResponse.setOrderId(orderDetail.getOrder().getId())
						.setProductId(orderDetail.getProduct().getId());
		return orderDetailResponse;
	}
	
	private OrderDetail retrieveOrderDetailById(int id) {
		return  orderDetailRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Not found order detail with id =" + id));
	}

	private Product retrieveProductById(int product_id) {
		return productRepository.findById(product_id)
				.orElseThrow(() -> new DataNotFoundException("Not found product with id =" + product_id));
	}

	private Order retrieveOrderById(int order_id) {
		return orderRepository.findById(order_id)
				.orElseThrow(() -> new DataNotFoundException("Not found product with id =" + order_id));
	}
	
}
