package it.geosolutions.mapstore.dto;

import it.geosolutions.mapstore.model.Rasvjeta;

import java.util.List;

public class RasvjetaDTO {
    private List<Rasvjeta> rasvjeta;
    private Integer count;

    public RasvjetaDTO(List<Rasvjeta> rasvjeta, Integer count) {
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
