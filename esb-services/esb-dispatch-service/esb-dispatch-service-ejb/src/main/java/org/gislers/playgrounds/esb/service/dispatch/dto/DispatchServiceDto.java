package org.gislers.playgrounds.esb.service.dispatch.dto;

import org.gislers.playgrounds.esb.common.message.ClientName;
import org.gislers.playgrounds.esb.common.message.ServiceName;

import java.io.Serializable;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public class DispatchServiceDto implements Serializable {

    private ServiceName serviceName;
    private ClientName clientName;
    private String environmentName;
    private String messageVersion;
    private String txId;
    private String payload;

    private DispatchServiceDto() {
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public ClientName getClientName() {
        return clientName;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public String getTxId() {
        return txId;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DispatchServiceDto{");
        sb.append("serviceName=").append(serviceName);
        sb.append(", clientName=").append(clientName);
        sb.append(", environmentName='").append(environmentName).append('\'');
        sb.append(", messageVersion='").append(messageVersion).append('\'');
        sb.append(", txId='").append(txId).append('\'');
        sb.append(", payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private ServiceName serviceName;
        private ClientName clientName;
        private String environmentName;
        private String messageVersion;
        private String txId;
        private String payload;

        public Builder() {
        }

        public Builder serviceName(ServiceName serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder clientName(ClientName clientName) {
            this.clientName = clientName;
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

        public Builder txId(String txId) {
            this.txId = txId;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public DispatchServiceDto build() {
            DispatchServiceDto dispatchServiceDto = new DispatchServiceDto();
            dispatchServiceDto.clientName = this.clientName;
            dispatchServiceDto.environmentName = this.environmentName;
            dispatchServiceDto.messageVersion = this.messageVersion;
            dispatchServiceDto.payload = this.payload;
            dispatchServiceDto.serviceName = this.serviceName;
            dispatchServiceDto.txId = this.txId;
            return dispatchServiceDto;
        }
    }
}
