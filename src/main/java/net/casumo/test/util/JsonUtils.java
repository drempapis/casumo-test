package net.casumo.test.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.casumo.test.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JsonUtils {

    private ObjectMapper objectMapper;

    private Gson gson;

    public JsonUtils() {
        this.objectMapper = new ObjectMapper();
        this.gson = new GsonBuilder().create();
    }

    public <T> List<T> deserializeAsList(final String json, Class<T> clazz) {
        List<T> list = Collections.emptyList();
        try {
            if (json != null) {
                list = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            }
        } catch (IOException e) {
            throw new ValidationException("[json-serialization-failed]: json: " + json, e);
        }
        return list;
    }

    public String toJson(final Object object) {
        return gson.toJson(object);
    }

    public JsonNode toJsonNode(final String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException e) {
            throw new ValidationException("[json-serialization-failed]: json: " + json, e);
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}