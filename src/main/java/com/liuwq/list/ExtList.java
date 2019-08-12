package com.liuwq.list;

public interface ExtList<E> {

    int size();

    boolean isEmpty();

    boolean add(E e);

    E get(int index);

    E remove(int index);

}
