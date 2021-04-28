package it.geosolutions.mapstore.utils.search;

import org.springframework.stereotype.Component;

/**
 * This class is used for representation of entities in sql queries
 * it is used with SearchUtils class
 *
 */

@Component
public class SearchEntity {
    private String entity;
    private String fid;
    private String fk;

    public SearchEntity() {
    }

    public SearchEntity(String entity, String fid, String fk) {
        this.entity = entity;
        this.fid = fid;
        this.fk = fk;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
            "entity='" + entity + '\'' +
            ", fid='" + fid + '\'' +
            ", fk='" + fk + '\'' +
            '}';
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }
}
