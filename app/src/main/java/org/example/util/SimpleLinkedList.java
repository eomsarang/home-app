package org.example.util;

public class SimpleLinkedList<E> {
  private Node<E> head;
  private Node<E> tail;
  private int len;

  public void add(E value) {
    // 새 노드를 만든다.
    Node<E> node = new Node<>();

    // 새 노드에 값을 저장한다.
    node.value = value;

    // 리스트 개수를 증가시킨다.
    len++;

    // 마지막 노드가 없다면,
    // 새 노드를 시작 노드와 마지막 노드로 설정하고 함수 실행을 끝낸다.
    if (tail == null) {
      head = tail = node;
      return;
    }

    // 마지막 노드가 있다면,
    // 새 노드의 이전 노드로 마지막 노드의 주소를 저장한다.
    node.prev = tail;

    // 마지막 노드의 다음 노드로 새 노드의 주소를 저장한다.
    tail.next = node;

    // 새 노드가 마지막 노드가 되게한다.
    tail = node;
  }

  public int size() {
    return len;
  }

  public E get(int index) throws RuntimeException {
    if (index >= len || index < 0) {
      throw new RuntimeException("인덱스가 유효하지 않습니다.");
    }

    Node<E> cursor = head;
    int count = 0;

    while (count < index) {
      // 다음 노드로 이동 => cursor 레퍼런스에 다음 노드 객체 주소를 담는다
      cursor = cursor.next;
      count++;
    }

    return cursor.value;
  }

  public boolean remove(E value) {
    Node<E> current;
    current = head;

    while (current != null) {
      if (current.value == value) {
        if (current.prev != null) {
          current.prev.next = current.next; // 현재 노드를 기준으로 이전 노드와 다음 노드를 연결한다.
        }
        if (current.next != null) {
          current.next.prev = current.prev;
        }
        if (current == head) {
          head = current.next;
        }
        if (current == tail) {
          tail = current.prev;
        }
        len--;
        return true;
      }
      current = current.next;
    }
    return false;
  }

  static class Node<T> {
    T value;
    Node next;
    Node prev;
  }

  public static void main(String[] args) {
    SimpleLinkedList<String> list = new SimpleLinkedList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    list.add("ddd");
    list.add("eee");

    // System.out.println(list.get(0));
    // System.out.println(list.get(1));
    // System.out.println(list.get(2));
    // System.out.println(list.get(3));
    // System.out.println(list.get(4));
    // System.out.println(list.get(-1));

    System.out.println(list.remove("ccc"));
    // System.out.println(list.remove("ddd"));
    // System.out.println(list.remove("bbb"));
    System.out.println(list.remove("aaa"));
    System.out.println(list.remove("eee"));
    System.out.println(list.remove("bbb"));
    System.out.println(list.remove("ddd"));

    list.add("aaa");
    list.add("bbb");
    list.add("ccc");

    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + ",");
    }
    System.out.println();
  }
}
