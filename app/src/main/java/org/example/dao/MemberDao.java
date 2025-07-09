package org.example.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import org.example.vo.User;

public class MemberDao {
  private User[] users = new User[10000]; // 레퍼런스 배열
  private int len = 0;
  private String filename;

  public MemberDao(String filename) {
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

  public void insert(User user) throws Exception {
    for (int i = 0; i < len; i++) {
      if (users[i].email.equals(user.email)) {
        throw new Exception("이메일 중복!");
        // 메서드 실행을 종료하고 이 메서드를 호출한 쪽에 예외 객체를 던진다.
      }
    }
    this.users[this.len++] = user;
  }

  public User[] findAll() {
    User[] arr = Arrays.copyOf(users, len);

    // User[] arr = new User[len];
    // for (int i = 0; i < len; i++) {
    //   arr[i] = users[i];
    // }

    return arr;
  }

  public User findByEmail(String email) {
    for (int i = 0; i < len; i++) {
      if (users[i].email.equals(email)) {
        return users[i];
      }
    }
    return null;
  }
}
