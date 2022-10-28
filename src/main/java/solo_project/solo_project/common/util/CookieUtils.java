package solo_project.solo_project.common.util;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.StringUtils;

public class CookieUtils {

  public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0)
      return Optional.empty();

    return Arrays.stream(cookies)
        .filter(cookie -> StringUtils.equals(cookie.getName(), name))
        .findAny();
  }
}
