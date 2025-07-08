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
      BufferedReader in = new BufferedReader(new FileReader(this.filename));

      while (true) {
        String csv = in.readLine(); // "aaa,aaa@test.com,1111"
        if (csv == null) {
          break;
        }

        // CSV 문자열 ---> 객체 생성
        // - 팩토리 메서드를 호출하여 객체 생성
        users[len++] = User.fromCsv(csv);
      }

      // 파일 읽기가 끝난 후 자원을 해제시킨다. 그래야 다른 프로그램이 파일 자원을 사용할 수 있다.
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
      PrintWriter out = new PrintWriter(new FileWriter(this.filename));

      for (int i = 0; i < this.len; i++) {
        if (this.users[i].email.equals("")) {
          continue;
        }
        out.println(this.users[i].toCsvString());
      }

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

    public User() {}

    // 팩토리 메서드
    // - CSV 포맷 문자열을 가지고 User 객체를 생성하는 메서드
    public static User fromCsv(String csv) {
      String[] values = csv.split(",");

      User user = new User();
      user.name = values[0];
      user.email = values[1];
      user.password = values[2];

      return user;
    }

    public String toCsvString() {
      return String.format("%s,%s,%s", this.name, this.email, this.password);
    }
  }
}
