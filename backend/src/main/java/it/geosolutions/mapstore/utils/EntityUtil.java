package it.geosolutions.mapstore.utils;

import java.util.*;

/**
 * Is used to whitelist entities for sql queries
 *
 */

public interface EntityUtil {
    List<String> ENTITIES = Arrays.asList("pokojnici", "grobovi", "groblja");
    static boolean isEntity(String entity) {
        return ENTITIES.contains(entity);
    }
}
