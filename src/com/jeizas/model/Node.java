package com.jeizas.model;

/**
 * Created by jeizas on 6/24/17.
 */
public class Node<T> {

    public T value;
    public Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", next=" + next +
                '}';
    }
}
