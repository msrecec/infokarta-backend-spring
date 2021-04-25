package it.geosolutions.mapstore.utils.search;

import org.springframework.stereotype.Component;

@Component
public class SearchEntity {
    private String entity;
    private String fk;

    public SearchEntity() {
    }

    public SearchEntity(String entity, String fk) {
        this.entity = entity;
        this.fk = fk;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
            "entity='" + entity + '\'' +
            ", fk='" + fk + '\'' +
            '}';
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }
}
