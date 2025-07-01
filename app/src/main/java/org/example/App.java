package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    class User {
      String name;
      String email;
      String password;
    }

    System.out.println("[HomeApp] 애플리케이션");

    InputStream stdin = System.in;
    Scanner keyboard = new Scanner(stdin);

    User[] users = new User[10000]; // 레퍼런스 배열
    int len = 0;

    while (len < users.length) {
      users[len] = new User(); // 사용자 정보를 담을 인스턴스 생성하고 인스턴스 주소를 배열에 저장

      System.out.print("이름? ");
      users[len].name = keyboard.nextLine();

      System.out.print("이메일? ");
      users[len].email = keyboard.nextLine();

      System.out.print("암호? ");
      users[len].password = keyboard.nextLine();

      len++;

      System.out.print("계속하시겠습니까? (y/N): ");
      String response = keyboard.nextLine();
      if (!response.equalsIgnoreCase("y")) {
        break;
      }
    }

    for (int i = 0; i < len; i++) {
      System.out.printf(
          "이름: %s, 이메일: %s, 암호: %s\n", users[i].name, users[i].email, users[i].password);
    }
    keyboard.close();
  }
}
