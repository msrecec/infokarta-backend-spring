package it.geosolutions.mapstore.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Decodes incoming ISO-8859-1 to UTF-8 format to prevent character problems due to Tomcat 7 xml config
 * leaky abstraction that doesn't allow change of uri encoding.
 *
 */

public interface EncodingUtils {
    static String decodeISO88591(String iso88591) throws UnsupportedEncodingException {
        return new String(iso88591.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
