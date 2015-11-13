package org.gislers.playgrounds.esb.gateway.ejb.publish;

import org.gislers.playgrounds.esb.gateway.ejb.publish.dto.PublishProductDto;
import org.gislers.playgrounds.esb.gateway.ejb.publish.exception.PublishProductException;

import javax.ejb.Asynchronous;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
public interface PublishProduct {

    @Asynchronous
    void publishProduct(PublishProductDto productDto) throws PublishProductException;
}
