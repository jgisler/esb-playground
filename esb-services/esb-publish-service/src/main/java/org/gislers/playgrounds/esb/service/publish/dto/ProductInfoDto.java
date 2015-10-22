package org.gislers.playgrounds.esb.service.publish.dto;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
public class ProductInfoDto {

    private String txId;
    private String environmentName;
    private String messageVersion;
    private String timestamp;
    private String payload;

    private ProductInfoDto() {
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

    public static class Builder {

        private String txId;
        private String environmentName;
        private String messageVersion;
        private String timestamp;
        private String payload;

        public Builder() {
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
            productInfoDto.environmentName = this.environmentName;
            productInfoDto.messageVersion = this.messageVersion;
            productInfoDto.timestamp = this.timestamp;
            productInfoDto.txId = this.txId;
            productInfoDto.payload = this.payload;
            return productInfoDto;
        }
    }
}
