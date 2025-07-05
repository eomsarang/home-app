package org.example;

public class App {

  public static void main(String[] args) {

    MemberHandler.init();

    // 게시글을 담을 배열과 len 변수를 준비한다.
    BoardHandler board = new BoardHandler();
    board.boardName = "게시판";

    // 기술소개 게시글을 담을 배열과 len 변수를 준비한다.
    BoardHandler techBoard = new BoardHandler();
    techBoard.boardName = "기술소개";

    // 게시글 배열을 초기화
    BoardHandler.init(board);

    // 기술 소개 게시글 배열을 초기화
    BoardHandler.init(techBoard);

    printMainMenu();

    loop:
    while (true) {
      String input = Prompt.inputString("메인>");

      switch (input) {
        case "1":
          MemberHandler.execute();
          break;
        case "2":
          BoardHandler.run(board);
          break;
        case "3":
          BoardHandler.run(techBoard);
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

    Prompt.keyboard.close();
  }

  static void printMainMenu() {
    System.out.println("[HomeApp] 애플리케이션");
    System.out.println("  1. 회원 관리");
    System.out.println("  2. 게시글 관리");
    System.out.println("  3. 기술소개 게시글 관리");
    System.out.println("  0. 종료");
  }

  static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }
}
