package org.example.util;

public interface SimpleList<E> {
  public abstract void add(E obj);

  public abstract int size();

  public abstract E get(int index) throws RuntimeException;

  public abstract boolean remove(E obj);
}
