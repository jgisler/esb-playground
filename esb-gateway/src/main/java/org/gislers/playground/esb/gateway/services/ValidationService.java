package org.gislers.playground.esb.gateway.services;

import org.gislers.playground.esb.gateway.config.Constants;
import org.gislers.playground.esb.gateway.dto.ProductDto;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by jim
 * Created on 10/4/15.
 */
@Named
public class ValidationService {

    public List<String> validate(ProductDto productDto) {

        List<String> errors = new ArrayList<>();

        if( isBlank(productDto.getEnvironmentName()) ) {
            errors.add( String.format("Validation Error: Missing header for '%s'", Constants.ENV_NAME) );
        }

        if( isBlank(productDto.getMessageVersion()) ) {
            errors.add( String.format("Validation Error: Missing header for '%s'", Constants.MESSAGE_VERSION) );
        }

        if( isBlank(productDto.getPayload())) {
            errors.add( "Validation Error: Payload body is missing" );
        }

        return errors;
    }
}
