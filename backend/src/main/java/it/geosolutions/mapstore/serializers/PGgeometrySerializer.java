package it.geosolutions.mapstore.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.postgis.PGgeometry;

import java.io.IOException;

public class PGgeometrySerializer extends JsonSerializer<PGgeometry> {
    @Override
    public void serialize(PGgeometry geom, JsonGenerator json, SerializerProvider provider) throws IOException {

        json.writeStartObject();

        json.writeStringField("type", geom.getGeometry().getTypeString());

        json.writeFieldName("crs");

        json.writeStartObject();

        json.writeStringField("type", "name");

        json.writeFieldName("properties");

        json.writeStartObject();

        json.writeStringField("name", "EPSG:"+geom.getGeometry().getSrid());

        json.writeEndObject();

        json.writeEndObject();

        json.writeFieldName("coordinates");

        json.writeStartArray();

        json.writeNumber(geom.getGeometry().getPoint(0).getX());

        json.writeNumber(geom.getGeometry().getPoint(0).getY());

        json.writeEndArray();

        json.writeEndObject();

    }

}
