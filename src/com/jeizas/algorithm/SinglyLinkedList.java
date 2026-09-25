package com.jeizas.algorithm;

import com.jeizas.model.Node;

/**
 * 带头结点的单链表，root 为头结点，不存数据
 * Created by jeizas on 6/24/17.
 */
public class SinglyLinkedList<T> {

    public Node<T> root = new Node<>(null);
    public int size;

    public void insert(T val) {

        Node<T> node = new Node<>(val);
        node.next = root.next;
        root.next = node;
        size ++;
    }

    public void reverse() {

        Node<T> previousNode = null;
        Node<T> currentNode = root.next;
        Node<T> nextNode;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode =  nextNode;
        }
        root.next = previousNode;
    }

    public void display() {
        Node<T> node = root.next;
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
}
