package org.gislers.playground.esb.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jim
 * Created on 10/4/15.
 */
public class GatewayResponse implements Serializable {

    private UUID transactionId;
    private List<ErrorItem> errorItems = new ArrayList<>();

    public GatewayResponse() {
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public List<ErrorItem> getErrorItems() {
        return errorItems;
    }

    public void setErrorItems(List<ErrorItem> errorItems) {
        this.errorItems = errorItems;
    }
}
