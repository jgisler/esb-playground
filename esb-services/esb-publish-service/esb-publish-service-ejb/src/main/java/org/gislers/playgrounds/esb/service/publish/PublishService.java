package org.gislers.playgrounds.esb.service.publish;

import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;
import org.gislers.playgrounds.esb.service.publish.exception.PublishException;

/**
 * Created by:   jgisle
 * Created date: 10/12/15
 */
public interface PublishService {

    void publish(ProductInfoDto productInfoDto) throws PublishException;
}
