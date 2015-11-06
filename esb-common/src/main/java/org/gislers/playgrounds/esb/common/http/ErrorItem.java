package org.gislers.playgrounds.esb.common.http;

import java.util.UUID;

/**
 * Created by jim
 * Created on 10/4/15.
 */
public class ErrorItem {

    private UUID errorId;
    private String errorDescription;

    public ErrorItem(String errorDescription) {
        this.errorId = UUID.randomUUID();
        this.errorDescription = errorDescription;
    }

    public UUID getErrorId() {
        return errorId;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
