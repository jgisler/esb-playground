package org.gislers.playgrounds.esb.consumer.product;

import org.gislers.playgrounds.esb.common.message.ServiceName;
import org.gislers.playgrounds.esb.consumer.AbstractEsbMdb;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public abstract class AbstractProductMdb extends AbstractEsbMdb {

    @Override
    protected ServiceName getServiceName() {
        return ServiceName.PRODUCT;
    }
}
