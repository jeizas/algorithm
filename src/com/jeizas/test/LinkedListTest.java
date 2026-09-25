package com.jeizas.test;

import com.jeizas.algorithm.SinglyLinkedList;

/**
 * Created by jeizas on 6/24/17.
 */
public class LinkedListTest {

    public static void main(String[] args) {

        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<>();

        for (int i = 0; i < 10; i++) {
            linkedList.insert(i);
        }

        linkedList.display();

        linkedList.reverse();

        linkedList.display();
    }
}
