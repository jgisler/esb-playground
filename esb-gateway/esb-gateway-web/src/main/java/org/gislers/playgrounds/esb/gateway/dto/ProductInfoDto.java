package org.gislers.playgrounds.esb.gateway.dto;

import org.gislers.playgrounds.esb.common.model.ProductInfo;

import java.io.Serializable;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
public class ProductInfoDto implements Serializable {

    private String txId;
    private String environmentName;
    private String messageVersion;
    private ProductInfo productInfo;

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

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public static class Builder {

        private String txId;
        private String environmentName;
        private String messageVersion;
        private ProductInfo productInfo;

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

        public Builder productInfo(ProductInfo productInfo) {
            this.productInfo = productInfo;
            return this;
        }

        public ProductInfoDto build() {
            ProductInfoDto productInfoDto = new ProductInfoDto();
            productInfoDto.environmentName = this.environmentName;
            productInfoDto.messageVersion = this.messageVersion;
            productInfoDto.txId = this.txId;
            productInfoDto.productInfo = this.productInfo;
            return productInfoDto;
        }
    }
}
