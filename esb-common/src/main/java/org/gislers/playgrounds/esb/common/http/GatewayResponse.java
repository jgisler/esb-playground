package org.gislers.playgrounds.esb.common.http;

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
    private long gatewayTimestamp;
    private List<ErrorItem> errorItems = new ArrayList<>();

    public GatewayResponse() {
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public long getGatewayTimestamp() {
        return gatewayTimestamp;
    }

    public void setGatewayTimestamp(long gatewayTimestamp) {
        this.gatewayTimestamp = gatewayTimestamp;
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
        sb.append(", gatewayTimestamp=").append(gatewayTimestamp);
        sb.append(", errorItems=").append(errorItems);
        sb.append('}');
        return sb.toString();
    }
}
