package com.cravin.marketplace;

import jakarta.persistence.*;

/**
 * This class defines a Purchaseed Model.
 * The purchased model is different than a model because it 
 * captures the order associated with the order, and the 
 * trained status of the model.
 */

@Entity
@Table(name = "purchased")
public class PurchasedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String orderID;

    @Column
    private String modelName;

    @Column 
    private boolean trained;

    @Column 
    private double price;

    public PurchasedModel () {
        
    }

    public PurchasedModel (String orderID, String modelName, boolean trained, double price) {
        this.orderID = orderID;
        this.modelName = modelName;
        this.trained = trained;
        this.price = price;
    }

    @Override
    public String toString() {
        return "PurchasedModel [id=" + id + ", orderID=" + orderID + ", modelName=" + modelName + ", trained=" + trained
                + ", price=" + price + "]";
    }

    public int getId() {
        return id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public boolean isTrained() {
        return trained;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
