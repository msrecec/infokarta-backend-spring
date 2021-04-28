package it.geosolutions.mapstore.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Validates different MIME types
 *
 */

public interface MIMETypeUtil {
      List<String> imgTypes = Arrays.asList(
          "jpg",
          "png",
          "jpeg"
      );
      List<String> docTypes = Arrays.asList(
          "pdf",
          "txt",
          "xlsx",
          "docx",
          "docm",
          "doc"
      );


      Map<String, String> mimeTypes = new HashMap<String, String>(){{
          put("jpg", "image/jpeg");
          put("jpeg", "image/jpeg");
          put("png", "image/png");
          put("pdf", "application/jpeg");
          put("txt", "application/txt");
          put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
          put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
          put("docm", "application/vnd.ms-word.document.macroEnabled.12");
          put("doc", "application/msword");
      }};

      public static Boolean isImage(String imgType) {
          return imgTypes.contains(imgType);
      }

      public static Boolean isDocument(String docType) {
          return docTypes.contains(docType);
      }

}
