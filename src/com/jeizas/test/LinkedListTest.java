package com.jeizas.test;

import com.jeizas.algorithm.LinkedList;

/**
 * Created by jeizas on 6/24/17.
 */
public class LinkedListTest {

    public static void main(String[] args) {

        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            linkedList.insert(i);
        }

        System.out.println("原始链表:");
        linkedList.display();

        linkedList.reverse();

        System.out.println("反转后链表:");
        linkedList.display();

        System.out.println("链表大小: " + linkedList.getSize());
    }
}
