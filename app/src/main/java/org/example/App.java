package org.example;

import org.example.dao.BoardDao;
import org.example.dao.MemberDao;
import org.example.handler.BoardHandler;
import org.example.handler.Handler;
import org.example.handler.MemberHandler;
import org.example.util.Prompt;
import org.example.util.SimpleArrayList;
import org.example.util.SimpleLinkedList;

public class App {

  private MemberDao memberDao = new MemberDao("member.csv", new SimpleArrayList<>());
  private Handler memberHandler = new MemberHandler("회원", memberDao);

  private BoardDao boardDao = new BoardDao("board.csv", new SimpleLinkedList<>());
  private Handler boardHandler = new BoardHandler("게시판", boardDao);

  // 기술소개 게시글을 담을 배열과 len 변수를 준비한다.
  private BoardDao techBoardDao = new BoardDao("tech-board.csv", new SimpleArrayList<>());
  private Handler techBoardHandler = new BoardHandler("기술소개", techBoardDao);

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
