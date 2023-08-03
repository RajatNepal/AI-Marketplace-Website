package com.cravin.marketplace;

import jakarta.persistence.*;

/**
 * This class defines AiModel Objects that are availabe for purchase.
 */

@Entity
@Table(name = "models")
public class AiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int modelId;
    @Column
    private boolean inCart = false;
    @Column
    private String modelName;
    @Column
    private double price;
    @Column
    private String description;
    @Column
    private String imgName;
    @Column
    private boolean trained;
    @Column
    private boolean hidden;



    public AiModel() {
    }

    public AiModel(String modelName, boolean inCart, double price, String description, String imgName, boolean trained) {
        this.modelName = modelName;
        this.inCart = inCart;
        this.price = price;
        this.description = description;
        this.imgName = imgName;
        this.trained = trained;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean getTrained() {
        return this.trained;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public boolean isInCart() {
        return this.inCart;
    }

    public boolean getInCart() {
        return this.inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgName() {
        return this.imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "{" +
                " modelId='" + getModelId() + "'" +
                ", inCart='" + isInCart() + "'" +
                ", modelName='" + getModelName() + "'" +
                ", price='" + getPrice() + "'" +
                ", description='" + getDescription() + "'" +
                ", imgName='" + getImgName() + "'" +
                ", trained='" + getTrained() + "'" +

                "}";
    }

}
