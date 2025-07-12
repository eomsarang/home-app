package org.example.util;

import java.util.Arrays;

public class SimpleArrayList<T> implements SimpleList<T> {
  private Object[] arr = new Object[5];
  private int len = 0;

  public void add(T obj) {
    if (len == arr.length) {
      // Object[] arr2 = new Object[arr.length + (arr.length / 2)];
      // for (int i = 0; i < arr.length; i++) {
      //   arr2[i] = arr[i];
      // }
      // this.arr = arr2;

      this.arr = Arrays.copyOf(arr, arr.length + (arr.length / 2));
    }
    arr[len++] = obj;
  }

  public int size() {
    return len;
  }

  public T get(int index) throws RuntimeException {
    if (index >= len || index < 0) {
      throw new RuntimeException("인덱스의 범위가 유효하지 않습니다.");
    }
    return (T) arr[index];
  }

  public boolean remove(T obj) {
    for (int i = 0; i < len; i++) {
      if (arr[i] == obj) {
        for (int j = i; j < len - 1; j++) {
          arr[j] = arr[j + 1];
        }
        arr[len - 1] = null;
        len--;
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    SimpleArrayList<String> list = new SimpleArrayList<String>();
    list.add("aaa");
    list.add("bbb");
    // list.add(new Post()); // what에 대해 지정된 타입이 아닌 경우 컴파일 오류
    list.add("ccc");
    list.add("ddd");
    // list.add(100); // what에 대해 지정된 타입이 아닌 경우 컴파일 오류
    list.add("eee");
    list.add("fff");

    // System.out.println(list.get(0));
    // System.out.println(list.get(3));
    // System.out.println(list.get(5));
    // System.out.println(list.get(-1));

    list.remove("ccc");

    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + ",");
    }
    System.out.println();
  }
}
