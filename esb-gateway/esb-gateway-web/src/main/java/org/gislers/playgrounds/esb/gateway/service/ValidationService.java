package org.gislers.playgrounds.esb.gateway.service;

import org.gislers.playgrounds.esb.common.http.ErrorItem;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by:   jgisle
 * Created date: 10/13/15
 */
@Named
@Singleton
public class ValidationService {

    @Inject
    private Logger logger;

    private static final String MISSING_HEADER_TEMPLATE = "Validation Error: Missing header for '%s'";
    private static final String MISSING_PAYLOAD_TEMPLATE = "Validation Error: Payload body is missing";

    public List<ErrorItem> validate(ProductInfoDto productDto) {

        List<ErrorItem> errors = new ArrayList<>();

        if( isBlank(productDto.getEnvironmentName()) ) {
            errors.add( new ErrorItem(String.format(MISSING_HEADER_TEMPLATE, MessageConstants.ENV_NAME)) );
        }

        if( isBlank(productDto.getMessageVersion()) ) {
            errors.add( new ErrorItem(String.format(MISSING_HEADER_TEMPLATE, MessageConstants.MESSAGE_VERSION)) );
        }

        if( productDto.getProductInfo() == null ) {
            errors.add( new ErrorItem(MISSING_PAYLOAD_TEMPLATE) );
        }

        return errors;
    }
}
