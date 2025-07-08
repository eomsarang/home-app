package org.example.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import org.example.vo.Post;

public class BoardDao {
  private Post[] posts = new Post[10000]; // 레퍼런스 배열
  private int len = 0;
  private String filename;

  public BoardDao(String filename) {
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

        // CSV 문자열 ----> 객체 생성
        // - 팩토리 메서드를 호출하여 객체 생성
        posts[len++] = Post.fromCsv(csv);
      }

      // 파일 읽기가 끝난 후 자원을 해제시킨다. 그래야 다른 프로그램이 파일 자원을 사용할 수 있다.
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
      PrintWriter out = new PrintWriter(new FileWriter(this.filename));

      for (int i = 0; i < this.len; i++) {
        if (this.posts[i].no == 0) {
          continue;
        }
        out.println(this.posts[i].toCsvString());
      }

      out.close();
    } catch (IOException ex) {
      System.out.printf("%s 파일로 저장하는 중에 오류가 발생했습니다.\n", this.filename);
    }
  }

  public void insert(Post post) {
    posts[len++] = post;
  }

  public Post[] findAll() {
    Post[] arr = Arrays.copyOf(posts, len);

    // Post[] arr = new Post[len];
    // for (int i = 0; i < len; i++) {
    //   arr[i] = posts[i];
    // }

    return arr;
  }

  public Post findByNo(int no) {
    for (int i = 0; i < len; i++) {
      if (posts[i].no == no) {
        return posts[i];
      }
    }
    return null;
  }
}
