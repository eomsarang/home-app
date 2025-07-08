package org.example.handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.example.util.Prompt;

public class BoardHandler implements Handler {
  // 인스턴스(instance) 변수(variables; field)
  private Post[] posts = new Post[10000]; // 레퍼런스 배열
  private int len = 0;
  private String boardName;
  private String filename;

  public BoardHandler(String boardName, String filename) {

    this.boardName = boardName;
    this.filename = filename;
    init();
  }

  private void init() {
    try {
      FileReader in = new FileReader(this.filename);

      // 한 줄씩 읽어서 리턴할 수 있는 기능을 가진 데코레이터를 붙인다.
      BufferedReader in2 = new BufferedReader(in);

      while (true) {
        String line = in2.readLine();
        if (line == null) {
          break;
        }

        String[] arr = line.split(",");

        Post post = new Post();
        post.no = Integer.parseInt(arr[0]);
        post.title = arr[1];
        post.content = arr[2];
        post.writer = arr[3];

        posts[len++] = post;
      }

      // 파일 읽기가 끝난 후 자원을 해제시킨다. 그래야 다른 프로그램이 파일 자원을 사용할 수 있다.
      in2.close();
      in.close();
      // 파일을 닫다가 실패 했을 때 할 일을 기술한다.
    } catch (FileNotFoundException ex) {
      // 파일을 찾지 못했을 때 해야할 일을 기술한다.
      System.out.printf("%s 파일이 없습니다.\n", this.filename);
    } catch (IOException ex) {
      System.out.printf("%s 파일을 닫을 수 없습니다.\n", this.filename);
    }
  }

  public void save() {
    try {
      FileWriter out = new FileWriter(this.filename);
      // 데코레이터 준비
      PrintWriter out2 = new PrintWriter(out);

      // 배열에 들어있는 데이터를 파일로 출력한다.
      for (int i = 0; i < this.len; i++) {
        if (this.posts[i].no == 0) {
          continue;
        }
        // 이전 코드보다 더 간결해짐
        out2.printf(
            "%d,%s,%s,%s\n", posts[i].no, posts[i].title, posts[i].content, posts[i].writer);
      }

      // 데이터 출력 작업이 끝난 후 자원을 해제시킨다.
      out2.close();
      out.close();
    } catch (IOException ex) {
      System.out.printf("%s 파일로 저장하는 중에 오류가 발생했습니다.\n", this.filename);
    }
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

    Post post = new Post(); // 사용자 정보를 담을 인스턴스 생성하고 인스턴스 주소를 배열에 저장

    post.no = Prompt.inputInt("번호?");
    post.title = Prompt.inputString("제목?");
    post.content = Prompt.inputString("내용?");
    post.writer = Prompt.inputString("작성자?");

    this.posts[this.len++] = post;

    System.out.println("입력되었습니다.");
  }

  private void handleList() {
    System.out.println("[목록]");

    for (int i = 0; i < this.len; i++) {
      if (this.posts[i].no == 0) {
        continue;
      }
      System.out.printf(
          "%d, %s, %s\n", this.posts[i].no, this.posts[i].title, this.posts[i].writer);
    }
  }

  private void handleRead() {
    System.out.println("[조회]");

    int no = Prompt.inputInt("번호?");
    for (int i = 0; i < this.len; i++) {
      Post post = this.posts[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (post.no == no) {
        System.out.printf("제목: %s\n", post.title);
        System.out.printf("내용: %s\n", post.content);
        System.out.printf("작성자: %s\n", post.writer);
        return;
      }
    }
    System.out.println("해당 게시글이 존재하지 않습니다.");
  }

  private void handleUpdate() {
    System.out.println("[변경]");

    int no = Prompt.inputInt("번호?");
    for (int i = 0; i < this.len; i++) {
      Post post = this.posts[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (post.no == no) {
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
        return;
      }
    }
    System.out.println("해당 게시글이 존재하지 않습니다.");
  }

  private void handleDelete() {
    System.out.println("[삭제]");

    int no = Prompt.inputInt("번호?");
    for (int i = 0; i < this.len; i++) {
      Post post = this.posts[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (post.no == no) {
        String response = Prompt.inputString("정말 삭제하시겠습니까? (Y/n)");
        if (response.equalsIgnoreCase("Y") || response.equals("")) {
          post.no = 0;
          System.out.println("삭제했습니다.");
        } else {
          System.out.println("삭제 취소했습니다.");
        }
        return;
      }
    }
    System.out.println("해당 게시글이 존재하지 않습니다.");
  }

  static class Post {
    int no;
    String title;
    String content;
    String writer;
  }
}
