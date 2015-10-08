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
@Table(name="credential")
public class CredentialEntity extends BaseEntity {

    @Id
    @Column(name="credential_id")
    private Long credentialId;
    private String username;
    private String password;

    public CredentialEntity() {
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
