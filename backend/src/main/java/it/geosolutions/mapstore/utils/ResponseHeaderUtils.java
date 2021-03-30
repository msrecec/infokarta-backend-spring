package it.geosolutions.mapstore.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseHeaderUtils {

    static void headerResponseWithJSON(HttpServletResponse response, String json) throws IOException {

        response.addHeader("Content-type", "application/json; charset=utf-8");

        response.getOutputStream().write(json.getBytes("UTF-8"));

        response.getOutputStream().flush();
    }
}
