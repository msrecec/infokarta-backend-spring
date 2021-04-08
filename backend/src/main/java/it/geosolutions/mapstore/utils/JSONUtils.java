package it.geosolutions.mapstore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.geosolutions.mapstore.serializers.PGgeometrySerializer;
import org.postgis.PGgeometry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface JSONUtils {
    static <T> String fromListToJSON(List<T> list) {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(out, list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] data = out.toByteArray();

        return new String(data);
    }

    static <P> String fromPOJOToJSON(P pojo) {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    static <P> P fromJSONtoPOJO(String json, Class<P> typeParameterClass ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Class<P> typeClass = typeParameterClass;
        P p = objectMapper.readValue(json, typeClass);

        return p;
    }

    static String fromGeomToJSON(PGgeometry geom) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule s = new SimpleModule();
        s.addSerializer(PGgeometry.class, new PGgeometrySerializer());

        objectMapper.registerModule(s);

        String json = objectMapper.writeValueAsString(geom);

        return json;
    }
}
