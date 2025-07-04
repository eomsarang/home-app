package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class App {
  static InputStream stdin = System.in;
  static Scanner keyboard = new Scanner(stdin);

  public static void main(String[] args) {
    System.out.println("[HomeApp] 애플리케이션");

    MemberHandler.init();

    printMenu();

    loop:
    while (true) {
      String input = prompt("메뉴>");

      switch (input) {
        case "1":
          MemberHandler.handleMemberCreate();
          break;
        case "2":
          MemberHandler.handleMemberList();
          break;
        case "3":
          MemberHandler.handleMemberRead();
          break;
        case "4":
          MemberHandler.handleMemberUpdate();
          break;
        case "5":
          MemberHandler.handleMemberDelete();
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

  static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
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
