package com.zaroyan.userordersapi.repositories;

import com.zaroyan.userordersapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zaroyan
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
