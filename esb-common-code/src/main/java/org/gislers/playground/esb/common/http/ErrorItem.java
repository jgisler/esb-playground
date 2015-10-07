package org.gislers.playground.esb.common.http;

import java.util.UUID;

/**
 * Created by jim
 * Created on 10/4/15.
 */
public class ErrorItem {

    private UUID errorId;
    private String errorDescription;

    public ErrorItem(UUID errorId, String errorDescription) {
        this.errorId = errorId;
        this.errorDescription = errorDescription;
    }

    public UUID getErrorId() {
        return errorId;
    }

    public void setErrorId(UUID errorId) {
        this.errorId = errorId;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
