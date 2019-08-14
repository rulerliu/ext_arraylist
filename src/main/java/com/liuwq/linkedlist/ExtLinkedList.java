package com.liuwq.linkedlist;

import com.liuwq.list.ExtList;

import java.util.NoSuchElementException;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/8/14 0014 下午 3:51
 * @version: V1.0
 */
public class ExtLinkedList<E> implements ExtList<E> {
    protected transient int modCount = 0;

    transient int size = 0;

    transient ExtLinkedList.Node<E> first;

    transient ExtLinkedList.Node<E> last;


    public ExtLinkedList() {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    void linkLast(E e) {
        // 得到最后的节点
        final ExtLinkedList.Node<E> l = last;
        // 新建一个节点, 放到链表最后
        final Node newNode = new Node(l, e, null);
        last = newNode;

        // 链表中没有数据, 把新节点放到第一个
        if (l == null) {
            first = newNode;
        } else {
            // 链表中有数据, 把新节点放到最后的节点后面
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        // 检查下标是否越界
        checkElementIndex(index);
        // 折半查找
        return node(index).item;
    }

    ExtLinkedList.Node<E> node(int index) {
        // 如果下标在链表前一半
        if (index < (size >> 1)) {
            ExtLinkedList.Node<E> x = first;
            // 从头开始查找
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            // 如果下标在链表后一半
            ExtLinkedList.Node<E> x = last;
            // 从尾开始查找
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    E unlink(Node<E> node) {
        // 获取当前节点三个元素
        final E element = node.item;
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;

        // 删除的节点是第一个
        if (prev == null) {
            // 把first改成当前节点next(第二个节点)
            first = next;
        } else {
            // 删除的不是第一个节点
            // 把上一个节点.next = 当前节点的next
            prev.next = next;
            // 当前节点的prev置为null, 帮助gc回收
            node.prev = null;
        }

        // 删除的是最后一个节点
        if (next == null) {
            // 把last改成当前节点的prev(倒数第二个节点)
            last = prev;
        } else {
            // 删除的不是最后一个节点
            // 把下一节点的prev = 当前节点的prev
            next.prev = prev;
            // 当前节点的next置为null, 帮助gc回收
            node.next = null;
        }

        // 当前节点的item置为null, 帮助gc回收
        node.item = null;
        size--;
        modCount++;
        return element;
    }

    public E remove() {
        return removeFirst();
    }

    public E removeFirst() {
        final ExtLinkedList.Node<E> f = first;
        // 链表里面没有node
        if (f == null) {
            throw new NoSuchElementException();
        }
        return unlinkFirst(f);
    }

    private E unlinkFirst(ExtLinkedList.Node<E> f) {
        final E element = f.item;
        final ExtLinkedList.Node<E> next = f.next;
        f.item = null;
        f.next = null; // help GC
        // 把first = 当前节点的netx(第二个node)
        first = next;
        // 只有一个node
        if (next == null) {
            // 把last置为null
            last = null;
        } else {
            // next的prev置为null(把第二个node的prev置为null, 使第二个变成了第一个)
            next.prev = null;
        }
        size--;
        modCount++;
        return element;
    }

    private static class Node<E> {
        ExtLinkedList.Node<E> prev;
        E item;
        ExtLinkedList.Node<E> next;

        Node(ExtLinkedList.Node<E> prev, E element, ExtLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

}
