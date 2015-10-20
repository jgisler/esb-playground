package org.gislers.playgrounds.esb.service.publish.dto;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
public class ProductInfoDto {

    private String txId;
    private String environmentName;
    private String messageVersion;
    private long   timestamp;
    private String payload;

    public ProductInfoDto() {
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
