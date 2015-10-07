package org.gislers.playground.esb.gateway.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.gislers.playground.esb.common.model.Product;

import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
@Named
public class SerializationService {

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper()
                .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true )
                .configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
    }

    public String toJson( Product product ) throws JsonProcessingException {
        return objectMapper.writeValueAsString( product );
    }

}
