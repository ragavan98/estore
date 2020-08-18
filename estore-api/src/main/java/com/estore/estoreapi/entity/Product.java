package com.estore.estoreapi.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name = "products", schema = "ccms")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private int qtyPerUnit;
    private int unitPrice;
    private long unitsInStock;
    private boolean active;

    public Product() {

    }

    public Product(String productName, int qtyPerUnit, int unitPrice, long unitsInStock, boolean active) {
        this.productName = productName;
        this.qtyPerUnit = qtyPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.active = active;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQtyPerUnit() {
        return qtyPerUnit;
    }

    public void setQtyPerUnit(int qtyPerUnit) {
        this.qtyPerUnit = qtyPerUnit;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(long unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



}
