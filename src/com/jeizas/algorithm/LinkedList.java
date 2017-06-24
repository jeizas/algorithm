package com.jeizas.algorithm;

import com.jeizas.model.Node;

/**
 * Created by jeizas on 6/24/17.
 */
public class LinkedList<T> {

    public Node<T> root;
    public int size;

    public void insert(int val) {

        Node node = new Node(val);
        if (root == null) {
            root = new Node(0);
            root.next = node;
        } else {
            node.next = root.next;
            root.next = node;
        }
    }

    public void reverse() {

        Node previousNode = null;
        Node currentNode = root.next;
        Node nextNode;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode =  nextNode;
        }
        root.next = previousNode;
    }

    public void display() {
        Node node = root.next;
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
}
