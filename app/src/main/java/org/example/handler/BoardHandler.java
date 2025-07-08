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
        String csv = in2.readLine();
        if (csv == null) {
          break;
        }

        // Refactoring 전:
        // Post post = new Post(line);
        // posts[len++] = post;

        // Refactoring 후: replace temp with query
        // 임시 변수가 있는 자리에 직접 값을 리턴하는 코드를 두는 기법
        // => 가능한 관련 코드를 가까이 두면 코드를 읽기 쉬워진다.
        posts[len++] = new Post(csv);
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
        // 이전에는 CSV 포맷을 여기에서 결정했다.
        // 문제는 Post 클래스에 새 필드가 추가된다면 여기 코드도 바꿔야 한다.
        // 그런데 다음과 같이 CSV 포맷을 Post 클래스에서 결정한다면
        // Post 클래스에 필드가 추가되더라도 여기 코드는 변경할 필요가 없다.
        out2.println(this.posts[i].toCsvString());
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

    // 기본 생성자 정의
    // - 다른 생성자가 있으면, 컴파일러는 기본 생성자를 만들어주지 않는다.
    // - 기본 생성자가 필요하면 개발자가 명시적으로 정의해야 한다.
    public Post() {}

    // CSV 문자열로 객체의 필드의 값을 초기화시키는 생성자
    public Post(String csv) {
      String[] values = csv.split(",");
      this.no = Integer.parseInt(values[0]);
      this.title = values[1];
      this.content = values[2];
      this.writer = values[3];
    }

    // 객체에 들어 있는 값을 CSV 포맷 문자열로 만드는 코드를
    // 그 값을 가지고 있는 클래스에 두는 것이 유지보수에 좋다.
    // => GRASP의 Information Expert
    public String toCsvString() {
      return String.format("%d,%s,%s,%s", this.no, this.title, this.content, this.writer);
    }
  }
}
