package com.anxpp.demo.elasticsearch.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JsonHelper {

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        objectMapper = new ObjectMapper();
    }

    public String string(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
