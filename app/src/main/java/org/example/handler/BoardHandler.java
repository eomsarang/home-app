package org.example.handler;

import java.util.ArrayList;
import org.example.dao.BoardDao;
import org.example.util.Prompt;
import org.example.vo.Post;

public class BoardHandler implements Handler {
  // 인스턴스(instance) 변수(variables; field)

  private String boardName;
  private BoardDao boardDao;

  public BoardHandler(String boardName, String filename) {

    this.boardName = boardName;
    this.boardDao = new BoardDao(filename);
  }

  public void service() {
    this.printMenu();

    loop:
    while (true) {
      String input = Prompt.inputString("메인/%s>", this.boardName);

      switch (input) {
        case "1":
          handleCreate();
          break;
        case "2":
          handleList();
          break;
        case "3":
          handleRead();
          break;
        case "4":
          handleUpdate();
          break;
        case "5":
          handleDelete();
          break;
        case "0":
          break loop;
        case "menu":
          printMenu();
          break;
        default:
          handleError();
      }
    }
  }

  public void save() {
    boardDao.save();
  }

  private void printMenu() {
    System.out.printf("%s 관리:\n", this.boardName);
    System.out.println("  1. 입력");
    System.out.println("  2. 목록");
    System.out.println("  3. 조회");
    System.out.println("  4. 변경");
    System.out.println("  5. 삭제");
    System.out.println("  0. 이전");
  }

  private static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }

  private void handleCreate() {
    System.out.println("[입력]");

    Post post = new Post();
    post.title = Prompt.inputString("제목?");
    post.content = Prompt.inputString("내용?");
    post.writer = Prompt.inputString("작성자?");
    boardDao.insert(post);
    System.out.println("입력되었습니다.");
  }

  private void handleList() {
    ArrayList<Post> posts = boardDao.findAll();

    System.out.println("[목록]");

    for (Post post : posts) {
      System.out.printf("%d, %s, %s\n", post.no, post.title, post.writer);
    }
  }

  private void handleRead() {
    System.out.println("[조회]");

    int no = Prompt.inputInt("번호?");
    Post post = boardDao.findByNo(no);
    if (post == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
      return;
    }
    System.out.printf("제목: %s\n", post.title);
    System.out.printf("내용: %s\n", post.content);
    System.out.printf("작성자: %s\n", post.writer);
  }

  private void handleUpdate() {
    System.out.println("[변경]");

    int no = Prompt.inputInt("번호?");
    Post post = boardDao.findByNo(no);
    if (post == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
      return;
    }
    String title = Prompt.inputString("제목(%s):", post.title);
    String content = Prompt.inputString("내용(%s):", post.content);

    String response = Prompt.inputString("정말 변경하시겠습니까? (Y/n)");
    if (response.equalsIgnoreCase("Y") || response.equals("")) {
      post.title = title;
      post.content = content;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }

  private void handleDelete() {
    System.out.println("[삭제]");

    int no = Prompt.inputInt("번호?");
    Post post = boardDao.findByNo(no);
    if (post == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
      return;
    }

    String response = Prompt.inputString("정말 삭제하시겠습니까? (Y/n)");
    if (response.equalsIgnoreCase("Y") || response.equals("")) {
      boardDao.delete(no);
      System.out.println("삭제했습니다.");
    } else {
      System.out.println("삭제 취소했습니다.");
    }
  }
}
