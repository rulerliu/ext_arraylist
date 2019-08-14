package com.liuwq.linkedlist;

import java.util.LinkedList;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/8/14 0014 下午 3:43
 * @version: V1.0
 */
public class Test {

    public static void main(String[] args) {
        LinkedList<Integer> l = new LinkedList<>();
        ExtLinkedList<Integer> list = new ExtLinkedList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list.get(2));
        System.out.println(list.get(8));
        System.out.println(list.remove());
        System.out.println(list.remove());
//        System.out.println(list.remove(2));
//        System.out.println(list.remove(7));

        for (int i = 0; i < list.size(); i++) {
            System.out.println("integer:" + list.get(i));
        }

        System.out.println(8 >> 1);
    }

}
