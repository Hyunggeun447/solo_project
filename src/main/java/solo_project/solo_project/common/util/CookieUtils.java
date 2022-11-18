package solo_project.solo_project.common.util;

import static solo_project.solo_project.domain.user.util.SecurityConstants.REFRESH_TOKEN;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.StringUtils;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;

public class CookieUtils {

  public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0)
      return Optional.empty();

    return Arrays.stream(cookies)
        .filter(cookie -> StringUtils.equals(cookie.getName(), name))
        .findAny();
  }

  public static void setRefreshToken(HttpServletResponse response, TokenInfo tokenInfo) {
    Cookie cookie = new Cookie(REFRESH_TOKEN, tokenInfo.getRefreshToken());
    cookie.setPath("/");
    cookie.setMaxAge(Math.toIntExact(tokenInfo.getRefreshTokenExpirationTime()));
    response.addCookie(cookie);
  }

}
