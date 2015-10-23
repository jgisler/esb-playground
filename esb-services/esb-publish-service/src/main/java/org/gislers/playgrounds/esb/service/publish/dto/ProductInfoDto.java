package org.gislers.playgrounds.esb.service.publish.dto;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
public class ProductInfoDto {

    private String batchId;
    private String txId;
    private String environmentName;
    private String messageVersion;
    private String timestamp;
    private String payload;

    private ProductInfoDto() {
    }

    public String getBatchId() {
        return batchId;
    }

    public String getTxId() {
        return txId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductInfoDto{");
        sb.append("batchId='").append(batchId).append('\'');
        sb.append(", txId='").append(txId).append('\'');
        sb.append(", environmentName='").append(environmentName).append('\'');
        sb.append(", messageVersion='").append(messageVersion).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {

        private String batchId;
        private String txId;
        private String environmentName;
        private String messageVersion;
        private String timestamp;
        private String payload;

        public Builder() {
        }

        public Builder batchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public Builder txId(String txId) {
            this.txId = txId;
            return this;
        }

        public Builder environmentName(String environmentName) {
            this.environmentName = environmentName;
            return this;
        }

        public Builder messageVersion(String messageVersion) {
            this.messageVersion = messageVersion;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public ProductInfoDto build() {
            ProductInfoDto productInfoDto = new ProductInfoDto();
            productInfoDto.batchId = this.batchId;
            productInfoDto.environmentName = this.environmentName;
            productInfoDto.messageVersion = this.messageVersion;
            productInfoDto.timestamp = this.timestamp;
            productInfoDto.txId = this.txId;
            productInfoDto.payload = this.payload;
            return productInfoDto;
        }
    }
}
