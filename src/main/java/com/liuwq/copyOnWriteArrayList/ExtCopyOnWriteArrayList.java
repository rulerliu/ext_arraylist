package com.liuwq.copyOnWriteArrayList;

import com.liuwq.list.ExtList;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/8/12 0012 下午 2:37
 * @version: V1.0
 */
public class ExtCopyOnWriteArrayList<E> implements ExtList<E> {

    final transient ReentrantLock lock = new ReentrantLock();

    private transient volatile Object[] array;

    public ExtCopyOnWriteArrayList() {
        setArray(new Object[0]);
    }

//    public ExtCopyOnWriteArrayList(Collection<? extends E> c) {
//        Object[] elements;
//        if (c.getClass() == ExtCopyOnWriteArrayList.class) {
//            elements = ((ExtCopyOnWriteArrayList<?>) c).getArray();
//        } else {
//            elements = c.toArray();
//            // c.toArray might (incorrectly) not return Object[] (see 6260652)
//            if (elements.getClass() != Object[].class) {
//                elements = Arrays.copyOf(elements, elements.length, Object[].class);
//            }
//        }
//        setArray(elements);
//    }

    final Object[] getArray() {
        return array;
    }

    final void setArray(Object[] a) {
        array = a;
    }

    @Override
    public int size() {
        return getArray().length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            // 1 拿到原来的数组
            Object[] elements = getArray();
            int len = elements.length;
            // 2 把原来的数组扩容 +1 个长度  返回新数组
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            // 3 新数组最后一个元素赋值为e
            newElements[len] = e;
            // 4 把新数组赋值给array
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E get(int index) {
        return get(getArray(), index);
    }

    private E get(Object[] a, int index) {
        return (E) a[index];
    }

    @Override
    public E remove(int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            // 1 拿到原来的数组
            Object[] elements = getArray();
            int len = elements.length;
            E oldValue = get(elements, index);
            // 计算需要移动的元素个数
            int numMoved = len - 1 - index;
            // 3 移除最后一个元素
            if (numMoved == 0) {
                setArray(Arrays.copyOf(elements, len - 1));
            } else {
                // 创建一个新数组 长度为原数组-1
                Object[] newElements = new Object[len - 1];
                // 复制0到index索引的元素到新数组
                System.arraycopy(elements, 0, newElements, 0, index);
                // 复制index+1到最后索引的元素到新数组
                System.arraycopy(elements, index + 1, newElements, index,
                        numMoved);
                setArray(newElements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }

}
