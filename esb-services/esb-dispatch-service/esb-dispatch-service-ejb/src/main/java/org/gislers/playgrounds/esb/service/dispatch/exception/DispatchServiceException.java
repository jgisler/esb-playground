package org.gislers.playgrounds.esb.service.dispatch.exception;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public class DispatchServiceException extends RuntimeException {

    public DispatchServiceException() {
        super();
    }

    public DispatchServiceException(String message) {
        super(message);
    }

    public DispatchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DispatchServiceException(Throwable cause) {
        super(cause);
    }
}
