package com.liuwq;

public interface ExtList<E> {

    int size();

    boolean add(E e);

    E get(int index);

    E remove(int index);

}
