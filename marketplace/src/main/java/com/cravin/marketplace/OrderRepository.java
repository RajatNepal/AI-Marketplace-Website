package com.cravin.marketplace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
 
/**
 * JPA Repository to Store all created Order objects.
 * See Order.java for entity class & table name
 */

public interface OrderRepository extends JpaRepository<Order, String> {
    /**
     * Custom SQL Query
     * @param customerID
     * @return List of Orders associated with a customerID
     */
    @Query("SELECT m FROM Order m WHERE m.customerID = :customerID")
    List<Order> findByCustomerID(@Param("customerID") int customerID);

    /**
     * Updates the status of the order associated with the ID
     * @param id
     * @param status
     */
    @Transactional
    @Modifying
    @Query("update Order o SET o.orderState = :status WHERE o.orderId = :id")
    void updateStatus(String id, String status);
}