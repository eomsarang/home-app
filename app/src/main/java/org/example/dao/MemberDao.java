package org.example.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.example.vo.User;

public class MemberDao {
  ArrayList<User> list = new ArrayList<>();
  private String filename;

  public MemberDao(String filename) {
    this.filename = filename;
    this.init();
  }

  private void init() {
    try {
      BufferedReader in = new BufferedReader(new FileReader(this.filename));

      while (true) {
        String csv = in.readLine();
        if (csv == null) {
          break;
        }
        list.add(User.fromCsv(csv));
      }

      in.close();

    } catch (FileNotFoundException ex) {
      System.out.printf("%s 파일이 없습니다!\n", this.filename);
    } catch (IOException ex) {
      System.out.printf("%s 파일을 닫을 수 없습니다!\n", this.filename);
    }
  }

  public void save() {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(this.filename));

      for (User user : list) {
        if (user.email.equals("")) {
          continue;
        }
        out.println(user.toCsvString());
      }

      out.close();
    } catch (IOException ex) {
      System.out.printf("%s 파일로 저장하는 중에 오류가 발생했습니다!\n", this.filename);
    }
  }

  public void insert(User user) throws Exception {
    for (User u : list) {
      if (u.email.equals(user.email)) {
        throw new Exception("이메일 중복!");
      }
    }
    list.add(user);
  }

  public ArrayList<User> findAll() {
    return this.list;
  }

  public User findByEmail(String email) {
    for (User user : list) {
      if (user.email.equals(email)) {
        return user;
      }
    }
    return null;
  }

  public User delete(String email) {
    for (User user : list) {
      if (user.email.equals(email)) {
        list.remove(user);
        return user;
      }
    }
    return null;
  }
}
