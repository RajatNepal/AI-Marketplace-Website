package com.cravin.marketplace;

import jakarta.persistence.*;

/**
 * This class defines an Order
 */

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;

    // customer details: name, address, email
    @Column
    private int customerID;

    // order details: total price, payment, state (new, cancelled, delivered)
    @Column
    private double orderPrice;

    @Column
    private String orderState;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    // public List<PurchasedModel> getModelsOrdered() {
        
    // }

}