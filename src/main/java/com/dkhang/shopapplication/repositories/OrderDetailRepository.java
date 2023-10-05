package com.dkhang.shopapplication.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dkhang.shopapplication.models.Order;
import com.dkhang.shopapplication.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

	List<OrderDetail> findByOrder(Order order);
	
}
