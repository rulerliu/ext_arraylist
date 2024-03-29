package com.liuwq.arraylist;

import com.liuwq.list.ExtList;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/8/7 0007 下午 4:03
 * @version: V1.0
 */
public class Test {

    public static void main(String[] args) {
        List l = new ArrayList();
        l.add(null);
        l.add(null);
        System.out.println(l.size());

        ExtList<Integer> list = new ExtArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.add(10);
        System.out.println(list);

        list.remove(0);

    }

}
