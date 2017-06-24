package com.jeizas.test;

import com.jeizas.algorithm.LinkedList;

/**
 * Created by jeizas on 6/24/17.
 */
public class LinkedListTest {

    public static void main(String[] args) {

        LinkedList linkedList = new LinkedList<Integer>();

        for (int i = 0; i < 10; i++) {
            linkedList.insert(i);
        }

        linkedList.display();

        linkedList.reverse();

        linkedList.display();
    }
}
