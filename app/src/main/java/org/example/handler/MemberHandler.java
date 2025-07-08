package org.example.handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.example.util.Prompt;

public class MemberHandler implements Handler {

  private User[] users = new User[10000]; // 레퍼런스 배열
  private int len = 0;
  private String title;
  private String filename;

  public MemberHandler(String title, String filename) {
    this.title = title;
    this.filename = filename;
    this.init();
  }

  private void init() {
    try {
      FileReader in = new FileReader(this.filename);

      // 한 줄씩 읽어서 리턴할 수 있는 기능을 가진 데코레이터를 붙인다.
      BufferedReader in2 = new BufferedReader(in);

      while (true) {
        String csv = in2.readLine(); // "aaa,aaa@test.com,1111"
        if (csv == null) {
          break;
        }

        // Refactoring 전:
        // User user = new User(line);
        // users[len++] = user;

        // Refactoring 후: replace temp with query
        // 임시 변수가 있는 자리에 직접 값을 리턴하는 코드를 두는 기법
        // => 가능한 관련 코드를 가까이 두면 코드를 읽기 쉬워진다.
        users[len++] = new User(csv);
      }

      // 파일 읽기가 끝난 후 자원을 해제시킨다. 그래야 다른 프로그램이 파일 자원을 사용할 수 있다.
      in2.close();
      in.close();

    } catch (FileNotFoundException ex) {
      // 파일을 찾지 못했을 때 해야 할 일을 기술한다.
      System.out.printf("%s 파일이 없습니다!\n", this.filename);
    } catch (IOException ex) {
      // 파일을 닫다가 실패 했을 때 할 일을 기술한다.
      System.out.printf("%s 파일을 닫을 수 없습니다!\n", this.filename);
    }
  }

  public void save() {
    try {
      FileWriter out = new FileWriter(this.filename);
      // 데코레이터 준비
      PrintWriter out2 = new PrintWriter(out);

      // 배열에 들어 있는 데이터를 파일로 출력한다.
      for (int i = 0; i < this.len; i++) {
        if (this.users[i].email.equals("")) {
          continue;
        }

        // 이전에는 CSV 포맷을 여기에서 결정했다.
        // 문제는 User 클래스에 새 필드가 추가된다면 여기 코드도 바꿔야 한다.
        // 그런데 다음과 같이 CSV 포맷을 User 클래스에서 결정한다면
        // User 클래스에 필드가 추가되더라도 여기 코드는 변경할 필요가 없다.
        out2.println(this.users[i].toCsvString());
      }

      // 데이터 출력 작업이 끝난 후 자원을 해제시킨다.
      out2.close();
      out.close();
    } catch (IOException ex) {
      System.out.printf("%s 파일로 저장하는 중에 오류가 발생했습니다!\n", this.filename);
    }
  }

  public void service() {
    printMenu();

    loop:
    while (true) {
      String input = Prompt.inputString("메인/회원>");

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
    System.out.printf("%s 관리:\n", this.title);
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
    User user = new User(); // 사용자 정보를 담을 인스턴스 생성하고 인스턴스 주소를 배열에 저장

    user.name = Prompt.inputString("이름?");
    user.email = Prompt.inputString("이메일?");
    user.password = Prompt.inputString("암호?");

    users[len++] = user;

    System.out.println("입력되었습니다.");
  }

  private void handleList() {
    for (int i = 0; i < len; i++) {
      if (users[i].email.equals("")) {
        continue;
      }
      System.out.printf(
          "이름: %s, 이메일: %s, 암호: %s\n", users[i].name, users[i].email, users[i].password);
    }
  }

  private void handleRead() {

    String email = Prompt.inputString("이메일?");
    for (int i = 0; i < len; i++) {
      User user = users[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (user.email.equals(email)) {
        System.out.printf("이름: %s\n", user.name);
        System.out.printf("이메일: %s\n", user.email);
        System.out.printf("암호: %s\n", user.password);
        return;
      }
    }
    System.out.println("해당 회원이 존재하지 않습니다.");
  }

  private void handleUpdate() {
    String email = Prompt.inputString("이메일?");
    for (int i = 0; i < len; i++) {
      User user = users[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (user.email.equals(email)) {
        String name = Prompt.inputString("이름(%s):", user.name);
        String password = Prompt.inputString("암호(%s):", user.password);
        String response = Prompt.inputString("정말 변경하시겠습니까? (Y/n)");
        if (response.equalsIgnoreCase("Y") || response.equals("")) {
          user.name = name;
          user.password = password;
          System.out.println("변경했습니다.");
        } else {
          System.out.println("변경 취소했습니다.");
        }
        return;
      }
    }
    System.out.println("해당 회원이 존재하지 않습니다.");
  }

  private void handleDelete() {
    String email = Prompt.inputString("이메일?");
    for (int i = 0; i < len; i++) {
      User user = users[i];
      // user레퍼런스가 가리키는 객체의 email 변수 값이 사용자가 입력한 email 변수 값과 같은지 비교해야 한다.
      if (user.email.equals(email)) {
        String response = Prompt.inputString("정말 삭제하시겠습니까? (Y/n)");
        if (response.equalsIgnoreCase("Y") || response.equals("")) {
          user.email = "";
          System.out.println("삭제했습니다.");
        } else {
          System.out.println("삭제 취소했습니다.");
        }
        return;
      }
    }
    System.out.println("해당 회원이 존재하지 않습니다.");
  }

  static class User {
    String name;
    String email;
    String password;

    // 기본 생성자 정의
    // - 다른 생성자가 있으면, 컴파일러는 기본 생성자를 만들어주지 않는다.
    // - 기본 생성자가 필요하면 개발자가 명시적으로 정의해야 한다.
    public User() {}

    // CSV 문자열로 객체의 필드 값을 초기화시키는 생성자
    public User(String csv) {
      String[] values = csv.split(","); // "aaa,aaa@test.com,1111"
      this.name = values[0];
      this.email = values[1];
      this.password = values[2];
    }

    // 객체에 들어 있는 값을 CSV 포맷 문자열로 만드는 코드를
    // 그 값을 갖고 있는 클래스에 두는 것이 유지보수에 좋다.
    // => GRASP의 Information Expert
    public String toCsvString() {
      return String.format("%s,%s,%s", this.name, this.email, this.password);
    }
  }
}
