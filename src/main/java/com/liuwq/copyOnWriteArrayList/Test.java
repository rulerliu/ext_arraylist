package com.liuwq.copyOnWriteArrayList;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/8/12 0012 下午 2:35
 * @version: V1.0
 */
public class Test {

    public static void main(String[] args) {
        ExtCopyOnWriteArrayList list = new ExtCopyOnWriteArrayList();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
        list.remove(1);
        System.out.println(list);
    }

}
