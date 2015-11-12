package org.gislers.playgrounds.esb.gateway.exception;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
public class EsbGatewayException extends RuntimeException {

    public EsbGatewayException() {
        super();
    }

    public EsbGatewayException(String message) {
        super(message);
    }

    public EsbGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsbGatewayException(Throwable cause) {
        super(cause);
    }
}
