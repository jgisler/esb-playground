package org.gislers.playgrounds.esb.consumer.ejb.dispatch.exception;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
public class DispatchMessageException extends RuntimeException {

    public DispatchMessageException() {
        super();
    }

    public DispatchMessageException(String message) {
        super(message);
    }

    public DispatchMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DispatchMessageException(Throwable cause) {
        super(cause);
    }
}
