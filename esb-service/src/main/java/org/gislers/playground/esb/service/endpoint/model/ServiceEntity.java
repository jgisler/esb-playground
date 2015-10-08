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
@Table(name="service")
public class ServiceEntity {

    @Id
    @Column(name="service_id")
    private Long serviceId;
    private String name;
    private String description;

    public ServiceEntity() {
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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
