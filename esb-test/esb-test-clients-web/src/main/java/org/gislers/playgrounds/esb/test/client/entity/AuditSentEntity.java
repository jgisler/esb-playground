package org.gislers.playgrounds.esb.test.client.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by:   jgisle
 * Created date: 11/4/15
 */
@Entity
@XmlRootElement
@Table(
    name = "audit_sent",
    uniqueConstraints = @UniqueConstraint(columnNames = "tx_id")
)
public class AuditSentEntity implements Serializable {

    @Id
    @Column(name="tx_id")
    private String txId;

    @NotNull
    private long timestamp;

    @OneToMany(
            fetch= FetchType.EAGER,
            cascade= CascadeType.ALL,
            mappedBy="auditSentEntity"
    )
    private List<AuditReceivedEntity> auditReceivedEntities;

    public AuditSentEntity() {
    }

    public AuditSentEntity(String txId, long timestamp) {
        this.txId = txId;
        this.timestamp = timestamp;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<AuditReceivedEntity> getAuditReceivedEntities() {
        return auditReceivedEntities;
    }
}
