package org.gislers.playground.esb.gateway.dto;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
public class ProductDto {

    private String txId;
    private String environmentName;
    private String messageVersion;
    private String payload;

    public ProductDto() {
    }

    public String getTxId() {
        return txId;
    }

    public ProductDto setTxId(String txId) {
        this.txId = txId;
        return this;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public ProductDto setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
        return this;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public ProductDto setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public ProductDto setPayload(String payload) {
        this.payload = payload;
        return this;
    }
}
