package org.example;

public class App {

  public static void main(String[] args) {

    MemberHandler.init();

    printMainMenu();

    loop:
    while (true) {
      String input = Prompt.inputString("메인>");

      switch (input) {
        case "1":
          MemberHandler.execute();
          break;
        case "2":
          System.out.println("게시글 관리 선택했습니다.");
          break;
        case "0":
          break loop;
        case "menu":
          printMainMenu();
          break;
        default:
          handleError();
      }
    }

    // printMenu();

    Prompt.keyboard.close();
  }

  static void printMainMenu() {
    System.out.println("[HomeApp] 애플리케이션");
    System.out.println("  1. 회원 관리");
    System.out.println("  2. 게시글 관리");
    System.out.println("  0. 종료");
  }

  static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }
}

/*
[HomeApp] 애플리케이션
  1. 회원 관리
  2. 게시글 관리
  0. 종료
메인> 1
회원관리:
  1. 입력
  2. 목록
  3. 조회
  4. 변경
  5. 삭제
  0. 이전
메인/회원관리> 1
이름? 홍길동
이메일? hong@test.com
암호? 1111
입력되었습니다.
메인/회원관리> 1
이름? 임꺽정
이메일? leem@test.com
암호? 2222
입력되었습니다.
메인/회원관리> 2
홍길동, hong@test.com, 1111
임꺽정, leem@test.com, 2222
메인/회원관리> 8
메뉴 번호가 유효하지 않습니다.
메인/회원관리> xx
메뉴 번호가 유효하지 않습니다.
메인/회원관리> 3
이메일? hong@test.com
이름: 홍길동
이메일: hong@test.com
암호: 1111
메인/회원관리> 3
이메일? hong@test.com
해당 회원이 존재하지 않습니다.
메인/회원관리> 4
이메일? hong@test.com
이름(홍길동): 홍길순
암호(1111): 2222
정말 변경하시겠습니까?(Y/n) Y
변경했습니다.
메인/회원관리> 5
이메일? hong@test.com
정말 삭제하시겠습니까?(Y/n) Y
삭제했습니다.
메인/회원관리> 0
[HomeApp] 애플리케이션
  1. 회원 관리
  2. 게시글 관리
  3. 종료
메인>
*/
