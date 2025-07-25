package org.example.handler;

import java.util.List;
import org.example.dao.MemberDao;
import org.example.util.Prompt;
import org.example.vo.User;

public class MemberHandler implements Handler {

  private String title;
  private MemberDao memberDao;

  public MemberHandler(String title, MemberDao memberDao) {
    this.title = title;
    this.memberDao = memberDao;
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

  public void save() {
    memberDao.save();
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
    System.out.println("[입력]");

    User user = new User();
    user.name = Prompt.inputString("이름?");
    user.email = Prompt.inputString("이메일?");
    user.password = Prompt.inputString("암호?");

    while (true) {
      try {
        memberDao.insert(user);
        System.out.println("입력되었습니다.");
        break;
      } catch (Exception e) {
        System.out.println("이미 존재하는 이메일입니다.");
        user.email = Prompt.inputString("이메일?");
      }
    }
  }

  private void handleList() {
    System.out.println("[목록]");
    List<User> users = memberDao.findAll();

    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      System.out.printf("이름: %s, 이메일: %s, 암호: %s\n", user.name, user.email, user.password);
    }
  }

  private void handleRead() {
    System.out.println("[조회]");
    String email = Prompt.inputString("이메일?");
    User user = memberDao.findByEmail(email);
    if (user == null) {
      System.out.println("해당 회원이 존재하지 않습니다.");
      return;
    }

    System.out.printf("이름: %s\n", user.name);
    System.out.printf("이메일: %s\n", user.email);
    System.out.printf("암호: %s\n", user.password);
  }

  private void handleUpdate() {
    System.out.println("[변경]");

    String email = Prompt.inputString("이메일?");
    User user = memberDao.findByEmail(email);
    if (user == null) {
      System.out.println("해당 회원이 존재하지 않습니다.");
      return;
    }

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
  }

  private void handleDelete() {
    System.out.println("[삭제]");
    String email = Prompt.inputString("이메일?");

    User user = memberDao.findByEmail(email);
    if (user == null) {
      System.out.println("해당 회원이 존재하지 않습니다.");
      return;
    }

    String response = Prompt.inputString("정말 삭제하시겠습니까? (Y/n)");
    if (response.equalsIgnoreCase("Y") || response.equals("")) {
      memberDao.delete(email);
      System.out.println("삭제했습니다.");
    } else {
      System.out.println("삭제 취소했습니다.");
    }
  }
}
