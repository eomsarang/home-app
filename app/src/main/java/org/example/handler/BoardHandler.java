package org.example.handler;

import org.example.util.Prompt;

public class BoardHandler implements Handler {
  // 인스턴스(instance) 변수(variables; field)
  private Post[] posts = new Post[10000]; // 레퍼런스 배열
  private int len = 0;
  private String boardName;

  // 생성자
  // - 인스턴스를 사용하는데 문제가 없도록 유효한 값으로 초기화시키는 기능
  // - new 명령을 실행할 때 지정한 생성자가 호출된다.
  //   예1) new BoardHandler() --> 파라미터가 없는 생성자가 호출된다.
  //   예1) new BoardHandler("텍스트") --> 문자열을 받는 생성자가 호출된다.
  public BoardHandler(String boardName) {
    // this는 내장 변수다. new 명령으로 생성한 인스턴스의 주소가 들어 있다.
    this.boardName = boardName;
    init(); // init() 메서드를 호출할 때 넘겨준 this 변수의 값은 init() 메서드의 내장 변수인 this 변수로 복사된다.
  }

  private void init() {
    // 예제 데이터를 미리 만들어 배열에 저장한다.
    Post post = new Post();
    post.no = 1;
    post.title = "제목입니다1";
    post.content = "내용입니다1";
    post.writer = "홍길동";
    this.posts[this.len] = post;
    this.len++;

    post = new Post();
    post.no = 2;
    post.title = "제목입니다2";
    post.content = "내용입니다2";
    post.writer = "임꺽정";
    this.posts[this.len] = post;
    this.len++;

    post = new Post();
    post.no = 3;
    post.title = "제목입니다3";
    post.content = "내용입니다3";
    post.writer = "유관순";
    this.posts[this.len] = post;
    this.len++;
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
