package rocketseat.com.passin.helpers;

import java.util.Random;

public class PinCode {
  public static String generate(Integer length) {
    Random random = new Random();
    StringBuilder pinCode = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int digit = random.nextInt(10);
      pinCode.append(digit);
    }

    return pinCode.toString();
  }
}
