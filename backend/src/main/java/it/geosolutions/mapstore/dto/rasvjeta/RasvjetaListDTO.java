package it.geosolutions.mapstore.dto.rasvjeta;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.serializers.RasvjetaListDTOSerializer;

import java.util.List;

@JsonSerialize(using = RasvjetaListDTOSerializer.class)
public class RasvjetaListDTO {
    private List<Rasvjeta> rasvjeta;
    private Integer count;

    public RasvjetaListDTO(List<Rasvjeta> rasvjeta, Integer count) {
        this.rasvjeta = rasvjeta;
        this.count = count;
    }

    public List<Rasvjeta> getRasvjeta() {
        return rasvjeta;
    }

    public void setRasvjeta(List<Rasvjeta> rasvjeta) {
        this.rasvjeta = rasvjeta;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RasvjetaDTO{" +
            "rasvjeta=" + rasvjeta +
            ", count=" + count +
            '}';
    }
}
