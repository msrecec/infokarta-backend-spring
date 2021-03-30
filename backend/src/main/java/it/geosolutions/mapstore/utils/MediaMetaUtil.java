package it.geosolutions.mapstore.utils;

import java.util.*;

public interface MediaMetaUtil {
    List<String> mediaMeta = Arrays.asList("pokojnici", "grobovi");
    static boolean isMeta(String entity) {
        return mediaMeta.contains(entity.toLowerCase());
    }
}
