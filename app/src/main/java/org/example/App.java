package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class App {
  static User[] users = new User[10000]; // 레퍼런스 배열
  static int len = 0;
  static InputStream stdin = System.in;
  static Scanner keyboard = new Scanner(stdin);

  public static void main(String[] args) {
    System.out.println("[HomeApp] 애플리케이션");

    printMenu();

    loop:
    while (true) {
      String input = prompt("메뉴>");

      switch (input) {
        case "1":
          handleMemberCreate();
          break;
        case "2":
          handleMemberList();
          break;
        case "6":
          break loop;
        case "menu":
          printMenu();
        default:
          handleError();
      }
    }

    keyboard.close();
  }

  static String prompt(String title) {
    System.out.print(title + " ");
    return keyboard.nextLine().trim();
  }

  static void printMenu() {
    System.out.println("메뉴: ");
    System.out.println("  1. 입력");
    System.out.println("  2. 목록");
    System.out.println("  3. 조회");
    System.out.println("  4. 변경");
    System.out.println("  5. 삭제");
    System.out.println("  6. 종료");
  }

  static void handleMemberCreate() {
    users[len] = new User(); // 사용자 정보를 담을 인스턴스 생성하고 인스턴스 주소를 배열에 저장

    users[len].name = prompt("이름?");
    users[len].email = prompt("이메일?");
    users[len].password = prompt("암호?");

    len++;

    System.out.println("입력되었습니다.");
  }

  static void handleMemberList() {
    for (int i = 0; i < len; i++) {
      System.out.printf(
          "이름: %s, 이메일: %s, 암호: %s\n", users[i].name, users[i].email, users[i].password);
    }
  }

  static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }

  static class User {
    String name;
    String email;
    String password;
  }
}
