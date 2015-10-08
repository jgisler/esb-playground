package org.gislers.playground.esb.service.endpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by:   jgisle
 * Created date: 10/8/15
 */
@Entity
@Table(name="business")
public class BusinessEntity {

    @Id
    @Column(name="business_id")
    private Long businessId;
    private String name;
    private String description;

    public BusinessEntity() {
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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
}
