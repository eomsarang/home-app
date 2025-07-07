package org.example;

import org.example.handler.BoardHandler;
import org.example.handler.Handler;
import org.example.handler.MemberHandler;
import org.example.util.Prompt;

public class App {

  // GRASP
  // 1) Polymorphism
  //    - 인터페이스의 레퍼런스는 해당 인터페이스 규칙을 따르는 클래스라면 어떤 객체든지 저장할 수 있다.
  // 2) Low Coupling
  //    - App 클래스가 직접적으로 다른 클래스를 사용하기 보다는 인터페이스를 사용함으로써
  //      특정 클래스에 종속되는 것을 막을 수 있다.
  private Handler memberHandler = new MemberHandler("회원", "member.csv");

  // 게시글을 담을 배열과 len 변수를 준비한다.
  private Handler boardHandler = new BoardHandler("게시판", "board.csv");

  // 기술소개 게시글을 담을 배열과 len 변수를 준비한다.
  private Handler techBoardHandler = new BoardHandler("기술소개", "tech-board.csv");

  public static void main(String[] args) {
    new App().service();
  }

  private void service() {

    printMainMenu();

    loop:
    while (true) {
      String input = Prompt.inputString("메인>");

      switch (input) {
        case "1":
          memberHandler.service();
          break;
        case "2":
          boardHandler.service();
          break;
        case "3":
          techBoardHandler.service();
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

    Prompt.close();
    save();
  }

  private static void printMainMenu() {
    System.out.println("[HomeApp] 애플리케이션");
    System.out.println("  1. 회원 관리");
    System.out.println("  2. 게시글 관리");
    System.out.println("  3. 기술소개 게시글 관리");
    System.out.println("  0. 종료");
  }

  private static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }

  private void save() {
    ((MemberHandler) memberHandler).save();
    ((BoardHandler) boardHandler).save();
    ((BoardHandler) techBoardHandler).save();
  }
}
