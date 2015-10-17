package org.gislers.playgrounds.esb.test.client.model;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public class ClientEndpointResponse {

    private String txId;
    private String service;
    private String client;

    public ClientEndpointResponse() {
    }

    public ClientEndpointResponse(String txId, String service, String client) {
        this.txId = txId;
        this.service = service;
        this.client = client;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
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
}
