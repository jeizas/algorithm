package com.jeizas.algorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SortTest {

    static Stream<Consumer<Sort>> sorts() {
        return Stream.of(Sort::insertSort, Sort::bubbleSort, Sort::simpleSelectionSort, Sort::binarySort);
    }

    private static void assertSorts(Consumer<Sort> sort, Integer... input) {
        Integer[] expected = input.clone();
        Arrays.sort(expected);
        sort.accept(new Sort(input));
        assertArrayEquals(expected, input);
    }

    @ParameterizedTest
    @MethodSource("sorts")
    void sortsEdgeCases(Consumer<Sort> sort) {
        assertSorts(sort);
        assertSorts(sort, 1);
        assertSorts(sort, 3, 1);
        assertSorts(sort, 2, 3, 1);
        assertSorts(sort, 1, 2, 3, 3);
        assertSorts(sort, 5, 5, 5, 5);
        assertSorts(sort, 1, 2, 3, 4, 5);
        assertSorts(sort, 5, 4, 3, 2, 1);
        assertSorts(sort, 1, 3, 6, 4, 9, 5, 1, 12, 21, 26, 6, 1);
    }

    @ParameterizedTest
    @MethodSource("sorts")
    void sortsRandomArrays(Consumer<Sort> sort) {
        Random random = new Random(42);
        for (int n = 0; n < 1000; n++) {
            Integer[] arr = new Integer[random.nextInt(20)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(10) - 5;
            }
            assertSorts(sort, arr);
        }
    }

    @Test
    void binarySearchReturnsPositionAfterEqualValues() {
        Sort sort = new Sort(new Integer[]{1, 2, 3, 3});
        assertEquals(0, sort.binarySearch(0, 3, 0));
        assertEquals(2, sort.binarySearch(0, 3, 2));
        assertEquals(4, sort.binarySearch(0, 3, 3));
        assertEquals(4, sort.binarySearch(0, 3, 9));
        assertEquals(3, sort.binarySearch(0, 2, 3));
    }
}
