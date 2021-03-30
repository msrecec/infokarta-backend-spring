package it.geosolutions.mapstore.utils;

import java.util.HashMap;
import java.util.Map;

public interface MediaMetaUtil {
    Map<String, String> mediaMeta = new HashMap<String, String>(){{
        put("pokojnici", "pokojnici_slike_meta");
        put("grobovi", "grobovi_slike_meta");
    }};
}
