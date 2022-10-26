package solo_project.solo_project.common.util;

import java.util.regex.Pattern;

public class Validation {

  public static void validateRegex(String target, String regex) {
    boolean matches = Pattern.compile(regex)
        .matcher(target)
        .matches();
    if (!matches) {
      throw new RuntimeException("입력값이 형식에 맞지 않습니다.");
    }
  }

}
