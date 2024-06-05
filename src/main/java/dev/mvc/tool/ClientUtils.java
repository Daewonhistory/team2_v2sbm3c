package dev.mvc.tool;

import jakarta.servlet.http.HttpServletRequest;

public class ClientUtils {
 
    public static String getRemoteIP(HttpServletRequest request) {
      String remoteAddr = "";
      // dfda
      if (request != null) {
        remoteAddr = request.getHeader("X-Forwarded-For");
        if (remoteAddr == null || "".equals(remoteAddr)) {
          remoteAddr = request.getRemoteAddr();
        }
      }

      return remoteAddr;

    }
}