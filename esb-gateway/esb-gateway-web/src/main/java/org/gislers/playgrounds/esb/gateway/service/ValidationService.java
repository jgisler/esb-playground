package org.gislers.playgrounds.esb.gateway.service;

import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by:   jgisle
 * Created date: 10/13/15
 */
@Named
public class ValidationService {

    public List<String> validate(ProductInfoDto productDto) {

        List<String> errors = new ArrayList<>();

        if( isBlank(productDto.getEnvironmentName()) ) {
            errors.add( String.format("Validation Error: Missing header for '%s'", MessageConstants.ENV_NAME) );
        }

        if( isBlank(productDto.getMessageVersion()) ) {
            errors.add( String.format("Validation Error: Missing header for '%s'", MessageConstants.MESSAGE_VERSION) );
        }

        if( isBlank(productDto.getPayload())) {
            errors.add( "Validation Error: Payload body is missing" );
        }

        return errors;
    }
}
