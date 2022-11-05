package solo_project.solo_project.domain.user.util;

public class SecurityConstants {

  public static final int MAXIMAL_NUMBER_OF_WRONG_PASSWORD = 5;
  public static final String LOGIN_FAILED_KEY_PREFIX = "auth:login-failed#";
  public static final String LOGOUT_KEY_PREFIX = "auth:logout#";

  public static final String LOGIN_ACCESS_TOKEN_PREFIX = "auth:login:access-token#";
  public static final String LOGIN_REFRESH_TOKEN_PREFIX = "auth:login:refresh-token#";

  public static final String ACCESS_TOKEN = "access_token";
  public static final String REFRESH_TOKEN = "refresh_token";

  public static final String BEARER_TYPE = "Bearer ";
  public static final String EMPTY_VALUE = "";

  public static final String AUTHORIZATION_HEADER = "Authorization";

}
