package org.example.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.example.vo.Post;

public class BoardDao {
  List<Post> list;
  private String filename;
  private int lastNo = 0;

  public BoardDao(String filename, List<Post> list) {
    this.filename = filename;
    this.list = list;
    this.init();
  }

  private void init() {
    try {
      BufferedReader in = new BufferedReader(new FileReader(this.filename));
      lastNo = Integer.parseInt(in.readLine());

      while (true) {
        String csv = in.readLine();
        if (csv == null) {
          break;
        }

        list.add(Post.fromCsv(csv));
      }

      in.close();
    } catch (FileNotFoundException ex) {
      System.out.printf("%s 파일이 없습니다.\n", this.filename);
    } catch (IOException ex) {
      System.out.printf("%s 파일을 닫을 수 없습니다.\n", this.filename);
    }
  }

  public void save() {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(this.filename));

      out.println(lastNo);
      for (int i = 0; i < list.size(); i++) {
        Post post = list.get(i);
        if (post.no == 0) {
          continue;
        }
        out.println(post.toCsvString());
      }

      out.close();
    } catch (IOException ex) {
      System.out.printf("%s 파일로 저장하는 중에 오류가 발생했습니다.\n", this.filename);
    }
  }

  public void insert(Post post) {
    post.no = ++lastNo;
    list.add(post);
  }

  public List<Post> findAll() {
    return list;
  }

  public Post findByNo(int no) {
    for (int i = 0; i < list.size(); i++) {
      Post post = list.get(i);
      if (post.no == no) {
        return post;
      }
    }
    return null;
  }

  public Post delete(int no) {
    for (int i = 0; i < list.size(); i++) {
      Post post = list.get(i);
      if (post.no == no) {
        list.remove(post);
        return post;
      }
    }
    return null;
  }
}
