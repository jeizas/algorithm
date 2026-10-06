package com.jeizas.algorithm;

import com.jeizas.model.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SinglyLinkedListTest {

    private static <T> List<T> toList(SinglyLinkedList<T> list) {
        List<T> out = new ArrayList<>();
        for (Node<T> node = list.root.next; node != null; node = node.next) {
            out.add(node.value);
        }
        return out;
    }

    @Test
    void emptyListCanBeDisplayedAndReversed() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.display();
        list.reverse();
        assertEquals(0, list.size);
        assertTrue(toList(list).isEmpty());
    }

    @Test
    void insertsAtHeadAndReverses() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.insert(i);
        }
        assertEquals(5, list.size);
        assertEquals(Arrays.asList(4, 3, 2, 1, 0), toList(list));

        list.reverse();
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), toList(list));
    }

    @Test
    void holdsNonIntegerValues() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.insert("b");
        list.insert("a");
        String first = list.root.next.value;
        assertEquals("a", first);
        assertEquals(Arrays.asList("a", "b"), toList(list));
    }

    @Test
    void nodeToStringDoesNotRecurseThroughList() {
        Node<Integer> head = new Node<>(0);
        Node<Integer> tail = head;
        for (int i = 1; i < 100_000; i++) {
            tail.next = new Node<>(i);
            tail = tail.next;
        }
        tail.next = head;
        assertEquals("Node{value=0, next=1}", head.toString());
    }
}
