package com.jeizas.algorithm;

import com.jeizas.model.Node;

/**
 * Created by jeizas on 6/24/17.
 */
public class LinkedList<T> {

    public Node<T> root;
    public int size;

    /**
     * 构造函数，初始化哑节点
     */
    public LinkedList() {
        root = new Node<>(null);
        size = 0;
    }

    /**
     * 头插法插入节点
     * @param val 要插入的值
     */
    public void insert(T val) {
        Node<T> node = new Node<>(val);
        node.next = root.next;
        root.next = node;
        size++;
    }

    /**
     * 反转链表
     */
    public void reverse() {
        Node<T> previousNode = null;
        Node<T> currentNode = root.next;
        Node<T> nextNode;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }
        root.next = previousNode;
    }

    /**
     * 显示链表内容
     */
    public void display() {
        Node<T> node = root.next;
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 获取链表大小
     * @return 链表节点数量
     */
    public int getSize() {
        return size;
    }
}
