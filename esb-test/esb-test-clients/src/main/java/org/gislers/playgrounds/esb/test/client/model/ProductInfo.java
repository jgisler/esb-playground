package org.gislers.playgrounds.esb.test.client.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jim
 * Created on 9/27/15.
 */
public class ProductInfo implements Serializable {

    long id;
    String name;
    String description;
    BigDecimal unitPrice;

    public ProductInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", unitPrice=").append(unitPrice);
        sb.append('}');
        return sb.toString();
    }
}
