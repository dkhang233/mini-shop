package com.dkhang.shopapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dkhang.shopapplication.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
