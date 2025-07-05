package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  static InputStream stdin = System.in;
  static Scanner keyboard = new Scanner(stdin);

  static String inputString(String title, Object... args) {
    System.out.printf(title + " ", args);
    return keyboard.nextLine().trim();
  }
}
