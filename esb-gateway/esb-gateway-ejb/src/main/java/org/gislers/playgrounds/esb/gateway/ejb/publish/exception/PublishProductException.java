package org.gislers.playgrounds.esb.gateway.ejb.publish.exception;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
public class PublishProductException extends Exception {

    public PublishProductException() {
        super();
    }

    public PublishProductException(String message) {
        super(message);
    }

    public PublishProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublishProductException(Throwable cause) {
        super(cause);
    }
}
