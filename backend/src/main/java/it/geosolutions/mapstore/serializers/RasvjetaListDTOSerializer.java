package it.geosolutions.mapstore.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.Rasvjeta;

import java.io.IOException;
import java.util.List;

public class RasvjetaListDTOSerializer extends JsonSerializer<RasvjetaListDTO> {

    @Override
    public void serialize(RasvjetaListDTO r, JsonGenerator json, SerializerProvider provider) throws IOException {

        List<Rasvjeta> rasvjetaList = r.getRasvjeta();

        json.writeStartObject();

        json.writeFieldName("rasvjeta");

        json.writeStartArray();

        for(Rasvjeta temp : rasvjetaList) {

            json.writeObject(temp);

        }

        json.writeEndArray();

        json.writeNumberField("count", r.getCount());

        json.writeEndObject();

    }
}
