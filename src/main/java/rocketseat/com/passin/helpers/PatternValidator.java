package rocketseat.com.passin.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternValidator {
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
  private static final String ZIPCODE_REGEX = "^\\d{8}$";

  public static Boolean isEmailValid(String email) {
    Pattern pattern = Pattern.compile(EMAIL_REGEX);

    Matcher matcher = pattern.matcher(email);

    return matcher.matches();
  }

  public static Boolean isPasswordValid(String password) {
    Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    Matcher matcher = pattern.matcher(password);

    return matcher.matches();
  }

  public static Boolean isCpfValid(String cpf) {
    cpf = cpf.replaceAll("[^0-9]", "");

    if (cpf.length() != 11)
        return false;

    if (cpf.matches("(\\d)\\1{10}"))
        return false;

    int sum = 0;
    int weight = 10;
    for (int i = 0; i < 9; i++) {
        sum += Integer.parseInt(String.valueOf(cpf.charAt(i))) * weight--;
    }
    int remainder = 11 - (sum % 11);
    int digit1 = (remainder == 10 || remainder == 11) ? 0 : remainder;

    sum = 0;
    weight = 11;
    for (int i = 0; i < 10; i++) {
        sum += Integer.parseInt(String.valueOf(cpf.charAt(i))) * weight--;
    }
    remainder = 11 - (sum % 11);
    int digit2 = (remainder == 10 || remainder == 11) ? 0 : remainder;

    return digit1 == Integer.parseInt(String.valueOf(cpf.charAt(9))) &&
           digit2 == Integer.parseInt(String.valueOf(cpf.charAt(10)));
  }

  public static Boolean isAddressZipcodeValid(String zipcode) {
    Pattern pattern = Pattern.compile(ZIPCODE_REGEX);

    Matcher matcher = pattern.matcher(zipcode);

    return matcher.matches();
  }
}
