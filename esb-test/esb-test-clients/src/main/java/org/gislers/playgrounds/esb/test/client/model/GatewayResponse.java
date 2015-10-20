package org.gislers.playgrounds.esb.test.client.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim
 * Created on 10/4/15.
 */
@XmlRootElement
public class GatewayResponse {

    private String txId;
    private List<ErrorItem> errorItems = new ArrayList<>();

    public GatewayResponse() {
    }

    public String getTxId() {
        return txId;
    }

    public GatewayResponse setTxId(String txId) {
        this.txId = txId;
        return this;
    }

    public List<ErrorItem> getErrorItems() {
        return errorItems;
    }

    public void setErrorItems(List<ErrorItem> errorItems) {
        this.errorItems = errorItems;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GatewayResponse{");
        sb.append("txId='").append(txId).append('\'');
        sb.append(", errorItems=").append(errorItems);
        sb.append('}');
        return sb.toString();
    }
}
