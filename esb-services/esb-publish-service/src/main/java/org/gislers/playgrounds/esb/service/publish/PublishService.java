package org.gislers.playgrounds.esb.service.publish;

import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;
import org.gislers.playgrounds.esb.service.publish.exception.PublishException;

import javax.ejb.Local;

/**
 * Created by:   jgisle
 * Created date: 10/12/15
 */
@Local
public interface PublishService {

    void publish(ProductInfoDto productInfoDto) throws PublishException;
}
