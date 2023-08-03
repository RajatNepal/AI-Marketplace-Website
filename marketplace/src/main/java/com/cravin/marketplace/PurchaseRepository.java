package com.cravin.marketplace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
/**
 * JPA Repository to Store a purchase of an AiModel
 * See PurchasedModel.java for entity class & table name
 */

public interface PurchaseRepository extends JpaRepository<PurchasedModel, Long> {
    /**
     * Custom SQL Query
     * @param orderID
     * @return List if Purchased Models associated withe the given Order ID
     */
    @Query("SELECT m FROM PurchasedModel m WHERE m.orderID = :orderID")
    List<PurchasedModel> findByOrderID(@Param("orderID") String orderID);
}