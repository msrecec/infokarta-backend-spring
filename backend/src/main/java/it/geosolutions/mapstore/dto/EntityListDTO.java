package it.geosolutions.mapstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.geosolutions.mapstore.serializers.EntityListDTOSerializer;

import java.util.List;

@JsonSerialize(using = EntityListDTOSerializer.class)
public class EntityListDTO {
    private List entityList;
    private Integer count;

    public EntityListDTO(List entityList, Integer count) {
        this.entityList = entityList;
        this.count = count;
    }

    @Override
    public String toString() {
        return "EntityListDTO{" +
            "entityList=" + entityList +
            ", count=" + count +
            '}';
    }

    public List getEntityList() {
        return entityList;
    }

    public void setEntityList(List entityList) {
        this.entityList = entityList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

