package org.gislers.playgrounds.esb.consumer.exception;

/**
 * Created by:   jgisle
 * Created date: 10/21/15
 */
public class EsbConsumerException extends RuntimeException {

    public EsbConsumerException() {
        super();
    }

    public EsbConsumerException(String message) {
        super(message);
    }

    public EsbConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsbConsumerException(Throwable cause) {
        super(cause);
    }
}
