package org.gislers.playgrounds.esb.test.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by:   jgisle
 * Created date: 11/4/15
 */
@Entity
@XmlRootElement
@Table(name="audit_received", uniqueConstraints = @UniqueConstraint(columnNames = "received_id"))
public class AuditReceivedEntity implements Serializable {

    @Id
    @Column(name="received_id")
    private String id;

    @Column(name="timestamp")
    private long timestamp;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="tx_id")
    private AuditSentEntity auditSentEntity;

    public AuditReceivedEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public AuditSentEntity getAuditMessageSentEntity() {
        return auditSentEntity;
    }

    public void setAuditSentEntity(AuditSentEntity auditSentEntity) {
        this.auditSentEntity = auditSentEntity;
    }
}
