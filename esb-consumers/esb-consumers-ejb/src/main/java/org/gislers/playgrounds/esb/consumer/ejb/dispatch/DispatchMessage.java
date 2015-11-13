package org.gislers.playgrounds.esb.consumer.ejb.dispatch;

import org.gislers.playgrounds.esb.consumer.ejb.dispatch.dto.DispatchDto;

import javax.ejb.Asynchronous;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public interface DispatchMessage {

    @Asynchronous
    void dispatchMessage(DispatchDto dispatchDto);
}
