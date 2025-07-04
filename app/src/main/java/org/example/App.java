package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class App {
  static User[] users = new User[10000]; // 레퍼런스 배열
  static int len = 0;
  static InputStream stdin = System.in;
  static Scanner keyboard = new Scanner(stdin);

  static void init() {
    // 예제 데이터를 미리 만들어 배열에 저장한다.
    User user = new User();
    user.name = "user1";
    user.email = "user1@test.com";
    user.password = "1111";
    users[len] = user;
    len++;

    user = new User();
    user.name = "user2";
    user.email = "user2@test.com";
    user.password = "2222";
    users[len] = user;
    len++;

    user = new User();
    user.name = "user3";
    user.email = "user3@test.com";
    user.password = "3333";
    users[len] = user;
    len++;
  }

  public static void main(String[] args) {
    System.out.println("[HomeApp] 애플리케이션");

    init();

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
        case "3":
          handleMemberRead();
          break;
        case "4":
          handleMemberUpdate();
          break;
        case "5":
          handleMemberDelete();
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

  static String prompt(String title, Object... args) {
    System.out.printf(title + " ", args);
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
      if (users[i].email.equals("")) {
        continue;
      }
      System.out.printf(
          "이름: %s, 이메일: %s, 암호: %s\n", users[i].name, users[i].email, users[i].password);
    }
  }

  static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }

  static void handleMemberRead() {

    String email = prompt("이메일?");
    for (int i = 0; i < len; i++) {
      User user = users[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (user.email.equals(email)) {
        System.out.printf("이름: %s\n", user.name);
        System.out.printf("이메일: %s\n", user.email);
        System.out.printf("암호: %s\n", user.password);
        return;
      }
    }
    System.out.println("해당 회원이 존재하지 않습니다.");
  }

  static void handleMemberUpdate() {
    String email = prompt("이메일?");
    for (int i = 0; i < len; i++) {
      User user = users[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (user.email.equals(email)) {
        String name = prompt("이름(%s):", user.name);
        String password = prompt("암호(%s):", user.password);
        String response = prompt("정말 변경하시겠습니까? (Y/n)");
        if (response.equalsIgnoreCase("Y") || response.equals("")) {
          user.name = name;
          user.password = password;
          System.out.println("변경했습니다.");
        } else {
          System.out.println("변경 취소했습니다.");
        }
        return;
      }
    }
    System.out.println("해당 회원이 존재하지 않습니다.");
  }

  static void handleMemberDelete() {
    String email = prompt("이메일?");
    for (int i = 0; i < len; i++) {
      User user = users[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (user.email.equals(email)) {
        String response = prompt("정말 삭제하시겠습니까? (Y/n)");
        if (response.equalsIgnoreCase("Y") || response.equals("")) {
          user.email = "";
          System.out.println("삭제했습니다.");
        } else {
          System.out.println("삭제 취소했습니다.");
        }
        return;
      }
    }
    System.out.println("해당 회원이 존재하지 않습니다.");
  }

  // 클래스?
  // - 관련된 기능(메소드 및 변수)을 그룹으로 묶는 역할
  // - 새 데이터 타입을 정의하는 역할
  static class User {
    String name;
    String email;
    String password;
  }
}

/*
[HomeApp] 애플리케이션
  1. 입력
  2. 목록
  3. 조회
  4. 변경
  5. 삭제
  6. 종료
메뉴> 1
이름? 홍길동
이메일? hong@test.com
암호? 1111
입력되었습니다.
메뉴> 1
이름? 임꺽정
이메일? leem@test.com
암호? 2222
입력되었습니다.
메뉴> 2
홍길동, hong@test.com, 1111
임꺽정, leem@test.com, 2222
메뉴> 8
메뉴 번호가 유효하지 않습니다.
메뉴> xx
메뉴 번호가 유효하지 않습니다.
메뉴> 3
이메일? hong@test.com
이름: 홍길동
이메일: hong@test.com
암호: 1111
메뉴> 3
이메일? hong@test.com
해당 회원이 존재하지 않습니다.
메뉴> 4
이메일? hong@test.com
이름(홍길동): 홍길순
암호(1111): 2222
정말 변경하시겠습니까?(Y/n) Y
변경했습니다.
메뉴> 5
이메일? hong@test.com
정말 삭제하시겠습니까?(Y/n) Y
삭제했습니다.
메뉴>
*/
