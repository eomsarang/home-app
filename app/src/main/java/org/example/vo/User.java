package org.example.vo;

public class User {
  public String name;
  public String email;
  public String password;

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
