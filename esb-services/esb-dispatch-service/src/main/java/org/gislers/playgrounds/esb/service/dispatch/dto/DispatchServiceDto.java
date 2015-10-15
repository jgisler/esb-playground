package org.gislers.playgrounds.esb.service.dispatch.dto;

import org.gislers.playgrounds.esb.common.message.ClientName;
import org.gislers.playgrounds.esb.common.message.ServiceName;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public class DispatchServiceDto {

    private ServiceName serviceName;
    private ClientName clientName;
    private String environmentName;
    private String messageVersion;
    private String txId;
    private String payload;

    public DispatchServiceDto() {
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public ClientName getClientName() {
        return clientName;
    }

    public void setClientName(ClientName clientName) {
        this.clientName = clientName;
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

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
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
}
