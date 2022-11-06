package solo_project.solo_project.common.regex;

public class CommonRegex {

  public static String EMAIL_REGEX_PATTERN = "^(.+)@(\\S+)$";

  public static final String NICKNAME_REGEX_PATTERN = "^.{2,8}$";

  public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\\-_=+]).{8,15}$";

  public static String PHONE_NUMBER_REGEX_PATTERN = "^\\d{3}-\\d{4}-\\d{4}$";




}
