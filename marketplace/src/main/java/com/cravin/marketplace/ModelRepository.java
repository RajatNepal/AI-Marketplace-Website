package com.cravin.marketplace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
 
/**
 * JPA Repository to Store all AI Models available for Purchase.
 * See AiModel.java for entity class & table name
 */

public interface ModelRepository extends JpaRepository<AiModel, Long> {
    /**
     * Custom SQL Query
     * @param inCart
     * @return List of AiModels in the cart
     */
    @Query("SELECT m FROM AiModel m WHERE m.inCart = :inCart")
    List<AiModel> findByInCart(@Param("inCart") boolean inCart);

    /**
     * Custom SQL Query
     * @param visible
     * @return List of visible Ai Models
     */
    @Query("SELECT m FROM AiModel m WHERE m.hidden = :visible")
    List<AiModel> getVisible(@Param("visible") boolean visible);

    /**
     * Removes all items from the cart
     */
    @Transactional
    @Modifying
    @Query("update AiModel m SET m.inCart=false WHERE m.inCart=true")
    void updateCart();

    /**
     * Updates the price of the item associated with the given ID
     * @param id
     * @param price
     */
    @Transactional
    @Modifying
    @Query("update AiModel m SET m.price = :price WHERE m.modelId = :id")
    void updatePrice(int id, double price);

    /**
     * Updates the description of the item associated with the given ID
     * @param id
     * @param description
     */
    @Transactional
    @Modifying
    @Query("update AiModel m SET m.description = :description WHERE m.modelId = :id")
    void updateDescription(int id, String description);

    /**
     * Updates the visibility of the item associated with the given ID
     * @param id
     * @param hidden
     */
    @Transactional
    @Modifying
    @Query("update AiModel m SET m.hidden = :hidden WHERE m.modelId = :id")
    void updateVisibility(int id, boolean hidden);


}
