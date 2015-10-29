package org.gislers.playgrounds.esb.test.client.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:   jgisle
 * Created date: 10/29/15
 */
public class AuditServiceDto {

    private Long sentTimestamp;
    private List<Long> receivedTimestamps = new ArrayList<>();

    public AuditServiceDto() {
        sentTimestamp = System.currentTimeMillis();
    }

    public Long getSentTimestamp() {
        return sentTimestamp;
    }

    public void setSentTimestamp(Long sentTimestamp) {
        this.sentTimestamp = sentTimestamp;
    }

    public List<Long> getReceivedTimestamps() {
        return receivedTimestamps;
    }

    public void setReceivedTimestamps(List<Long> receivedTimestamps) {
        this.receivedTimestamps = receivedTimestamps;
    }
}
