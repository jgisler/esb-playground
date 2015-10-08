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
@Table(name = "endpoint")
public class EndpointEntity extends BaseEntity {

    @Id
    @Column(name="endpoint_id")
    private Long endpointId;
    private String key;
    private String value;

    public EndpointEntity() {
    }

    public Long getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(Long endpointId) {
        this.endpointId = endpointId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
