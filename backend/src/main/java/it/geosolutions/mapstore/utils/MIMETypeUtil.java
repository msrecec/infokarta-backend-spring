package it.geosolutions.mapstore.utils;

import java.util.Arrays;
import java.util.List;

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

      public static Boolean isImage(String imgType) {
          return imgTypes.contains(imgType);
      }

      public static Boolean isDocument(String docType) {
          return docTypes.contains(docType);
      }

}
