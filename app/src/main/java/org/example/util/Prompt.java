package org.example.util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  private static InputStream stdin = System.in;
  private static Scanner keyboard = new Scanner(stdin);

  public static String inputString(String title, Object... args) {
    System.out.printf(title + " ", args);
    return keyboard.nextLine().trim();
  }

  public static int inputInt(String title, Object... args) {
    return Integer.parseInt(inputString(title, args));
  }

  public static void close() {
    keyboard.close();
  }
}
