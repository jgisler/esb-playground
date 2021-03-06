package org.gislers.playgrounds.esb.common.http;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@XmlRootElement
public class ClientEndpointResponse {

    private String service;
    private String client;
    private String txId;

    public ClientEndpointResponse() {
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }
}
