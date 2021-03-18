package it.geosolutions.mapstore.utils;

import java.io.UnsupportedEncodingException;

public interface EncodingUtils {
    static String decodeISO88591(String iso88591) throws UnsupportedEncodingException {
        return new String(iso88591.getBytes("iso-8859-1"), "UTF-8");
    }
}
