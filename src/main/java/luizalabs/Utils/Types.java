package luizalabs.Utils;

import java.util.regex.Pattern;

public class Types {
  public static boolean isString(String value) {
    return Pattern.matches("[a-zA-Z.,]+", value);
  }
}
