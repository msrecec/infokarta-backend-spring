package it.geosolutions.mapstore.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Is used for responding with application/json in HttpServlet response
 *
 */

public interface HeaderUtils {

    static void responseWithJSON(HttpServletResponse response, String json) throws IOException {

        response.addHeader("Content-type", "application/json; charset=utf-8");

        response.getOutputStream().write(json.getBytes("UTF-8"));

        response.getOutputStream().flush();
    }
}
