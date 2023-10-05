package com.dkhang.shopapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dkhang.shopapplication.models.Order;
import com.dkhang.shopapplication.models.User;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	public List<Order> findAllByUser(User user);

}
