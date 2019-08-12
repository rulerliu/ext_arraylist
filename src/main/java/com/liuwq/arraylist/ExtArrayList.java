package com.liuwq.arraylist;

import com.liuwq.list.ExtList;

import java.util.Arrays;
import java.util.Collection;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/8/7 0007 下午 3:12
 * @version: V1.0
 */
public class ExtArrayList<E> implements ExtList<E> {

    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * 初始化一个空数组
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 数组初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 不为空的数据个数
     *
     * @serial
     */
    private int size;

    protected transient int modCount = 0;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public ExtArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public ExtArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public ExtArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class) {
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            }
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    @Override
    public E remove(int index) {
        // 检查下标是否越界
        rangeCheck(index);

        modCount++;
        // 先获取数据, 用于返回
        E oldValue = elementData(index);

        // 要移动的数据个数
        int numMoved = size - 1 - index;
        // 不是移除最后一个数据
        if (numMoved > 0) {
            // 拷贝数组: 原数组 开始索引  目标数组 开始索引  移动个数
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        }
        // 把最后一个数据置空
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void ensureCapacityInternal(int minCapacity) {
        // 添加元素的时候, 如果数组为空的话
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 把数组扩容为10
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        // 如果数组满了, 进行扩容
        if (minCapacity - elementData.length > 0) {
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        // 每次扩容 n + n / 2
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 第一次数组长度为0时候扩容, 把数组长度变成10
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }

        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {// overflow
            throw new OutOfMemoryError();
        }
        // 数组的长度如果大于integer最大值-8, 则是integer最大值
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

}
