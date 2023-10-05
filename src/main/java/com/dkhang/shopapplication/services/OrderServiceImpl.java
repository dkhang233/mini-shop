package com.dkhang.shopapplication.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.dtos.OrderDTO;
import com.dkhang.shopapplication.enumtype.OrderStatus;
import com.dkhang.shopapplication.exceptionhandler.DataNotFoundException;
import com.dkhang.shopapplication.models.Order;
import com.dkhang.shopapplication.models.User;
import com.dkhang.shopapplication.repositories.OrderRepository;
import com.dkhang.shopapplication.repositories.UserRepository;
import com.dkhang.shopapplication.responses.OrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper ;

	@Override
	public OrderResponse createOrder(OrderDTO orderDTO) {
		Order order = OrderDTOMapToOrder(orderDTO, new Order());
		order.setOrderDate(LocalDateTime.now());
		order.setTrackingNumber(UUID.randomUUID().toString());
		order.setStatus(OrderStatus.pending);
		order.setActive(true);
		return OrderMapToOrderResponse(orderRepository.save(order), new OrderResponse());

	}

	@Override
	public List<OrderResponse> retrieveOrderByUserId(int user_id) {
		return orderRepository.findAllByUser(retrieveUserById(user_id)).stream()
				.filter(order -> order.getActive() == true)
				.map(order -> OrderMapToOrderResponse(order, new OrderResponse()))
				.toList();
	}


	@Override
	public OrderResponse retrieveOrderByIdWithResponseFormat(int id) {
		return OrderMapToOrderResponse(retrieveOrderById(id), new OrderResponse());
	}

	@Override
	public OrderResponse updateOrderById(int id, OrderDTO orderDTO) {
		Order order = retrieveOrderById(id);
		order = OrderDTOMapToOrder(orderDTO, order);
		return  OrderMapToOrderResponse(orderRepository.save(order), new OrderResponse());
	}

	@Override
	public void deleteOrderById(int id) {
		Order order = retrieveOrderById(id);
		order.setActive(false);
		orderRepository.save(order);
	}
	
	private Order retrieveOrderById(int id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Not found order with id = " + id));

		if (order.getActive() == false) {
			throw new DataNotFoundException("Order has been removed with id = " + id);
		}

		return order;
	}

	private User retrieveUserById(int user_id) {
		return userRepository.findById(user_id)
				.orElseThrow(() -> new DataNotFoundException("Not found user with id = " + user_id));
	}
	
	private Order OrderDTOMapToOrder(OrderDTO orderDTO , Order order) {
		modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
		modelMapper.map(orderDTO, order);
		order.setUser(retrieveUserById(orderDTO.getUserId()));
		order.setShippingDate(orderDTO.getShippingDate() == null ? LocalDate.now().plusDays(3) : orderDTO.getShippingDate());
		return order;
	}
	
	private OrderResponse OrderMapToOrderResponse(Order order , OrderResponse orderResponse) {
		modelMapper.typeMap(Order.class, OrderResponse.class);
		modelMapper.map(order,  orderResponse);
		orderResponse.setUserId(order.getUser().getId());
		return orderResponse;
	}

}
