package org.example;

public class TechBoardHandler {
  static Post[] posts = new Post[10000]; // 레퍼런스 배열
  static int len = 0;

  static void init() {
    // 예제 데이터를 미리 만들어 배열에 저장한다.
    Post post = new Post();
    post.no = 1;
    post.title = "제목입니다1";
    post.content = "내용입니다1";
    post.writer = "홍길동";
    posts[len] = post;
    len++;

    post = new Post();
    post.no = 2;
    post.title = "제목입니다2";
    post.content = "내용입니다2";
    post.writer = "임꺽정";
    posts[len] = post;
    len++;

    post = new Post();
    post.no = 3;
    post.title = "제목입니다3";
    post.content = "내용입니다3";
    post.writer = "유관순";
    posts[len] = post;
    len++;
  }

  static void service() {
    printMenu();

    loop:
    while (true) {
      String input = Prompt.inputString("메인/기술소개>");

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

  static void printMenu() {
    System.out.println("기술소개관리: ");
    System.out.println("  1. 입력");
    System.out.println("  2. 목록");
    System.out.println("  3. 조회");
    System.out.println("  4. 변경");
    System.out.println("  5. 삭제");
    System.out.println("  0. 이전");
  }

  static void handleError() {
    System.out.println("메뉴 번호가 유효하지 않습니다.");
  }

  static void handleCreate() {
    System.out.println("[입력]");

    Post post = new Post(); // 사용자 정보를 담을 인스턴스 생성하고 인스턴스 주소를 배열에 저장

    post.no = Prompt.inputInt("번호?");
    post.title = Prompt.inputString("제목?");
    post.content = Prompt.inputString("내용?");
    post.writer = Prompt.inputString("작성자?");

    posts[len++] = post;

    System.out.println("입력되었습니다.");
  }

  static void handleList() {
    System.out.println("[목록]");

    for (int i = 0; i < len; i++) {
      if (posts[i].no == 0) {
        continue;
      }
      System.out.printf("%d, %s, %s\n", posts[i].no, posts[i].title, posts[i].writer);
    }
  }

  static void handleRead() {
    System.out.println("[조회]");

    int no = Prompt.inputInt("번호?");
    for (int i = 0; i < len; i++) {
      Post post = posts[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (post.no == no) {
        System.out.printf("제목: %s\n", post.title);
        System.out.printf("내용: %s\n", post.content);
        System.out.printf("작성자: %s\n", post.writer);
        return;
      }
    }
    System.out.println("해당 기술소개 게시글이 존재하지 않습니다.");
  }

  static void handleUpdate() {
    System.out.println("[변경]");

    int no = Prompt.inputInt("번호?");
    for (int i = 0; i < len; i++) {
      Post post = posts[i];
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
    System.out.println("해당 기술소개 게시글이 존재하지 않습니다.");
  }

  static void handleDelete() {
    System.out.println("[삭제]");

    int no = Prompt.inputInt("번호?");
    for (int i = 0; i < len; i++) {
      Post post = posts[i];
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
    System.out.println("해당 기술소개 게시글이 존재하지 않습니다.");
  }

  static class Post {
    int no;
    String title;
    String content;
    String writer;
  }
}
