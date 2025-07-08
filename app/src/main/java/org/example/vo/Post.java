package org.example.vo;

public class Post {
  public int no;
  public String title;
  public String content;
  public String writer;

  public Post() {}

  // 팩토리 메서드
  // - CSV 포맷 문자열을 가지고 Post 객체를 생성하는 메서드
  public static Post fromCsv(String csv) {
    String[] values = csv.split(",");

    Post post = new Post();
    post.no = Integer.parseInt(values[0]);
    post.title = values[1];
    post.content = values[2];
    post.writer = values[3];

    return post;
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s", this.no, this.title, this.content, this.writer);
  }
}
