package org.gislers.playgrounds.esb.test.client.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:   jgisle
 * Created date: 10/29/15
 */
public class AuditServiceDto {

    private String txId;
    private List<Long> timings = new ArrayList<>(2);

    public AuditServiceDto() {
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public List<Long> getTimings() {
        return timings;
    }

    public void setTimings(List<Long> timings) {
        this.timings = timings;
    }
}
