package it.geosolutions.mapstore.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;

import java.io.IOException;
import java.util.List;

public class EntityListDTOSerializer extends JsonSerializer<EntityListDTO> {
    @Override
    public void serialize(EntityListDTO entityListDTO, JsonGenerator json, SerializerProvider provider) throws IOException {
        List<EntityListDTO> entityList = entityListDTO.getEntityList();

        json.writeStartObject();

        json.writeFieldName("data");

        json.writeStartArray();

        for(EntityListDTO temp : entityList) {

            json.writeObject(temp);

        }

        json.writeEndArray();

        json.writeNumberField("count", entityListDTO.getCount());

        json.writeEndObject();
    }
}
