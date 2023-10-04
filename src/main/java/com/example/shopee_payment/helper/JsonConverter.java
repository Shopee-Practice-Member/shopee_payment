package com.example.shopee_payment.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConverter {

    public static String writeValueAsString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            // convert user object to json string and return it
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            // catch various errors
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T readValue(String content, Class<T> valueType)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(content, valueType);
        } catch (JsonProcessingException jpEx) {
            throw new IllegalArgumentException("Can not cast content "+ content);
        }
    }


}
