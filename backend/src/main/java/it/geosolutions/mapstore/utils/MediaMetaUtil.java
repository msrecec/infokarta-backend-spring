package it.geosolutions.mapstore.utils;

import java.util.*;

public interface MediaMetaUtil {
    List<String> mediaMeta = Arrays.asList("pokojnici", "grobovi");
    static boolean isMeta(String entity) {
//        for(String s : mediaMeta) {
//            if(s.equalsIgnoreCase(entity)) {
//                return true;
//            }
//        }
//        return false;

        return mediaMeta.contains(entity.toLowerCase());

    }
}
