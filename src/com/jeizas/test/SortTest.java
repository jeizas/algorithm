package com.jeizas.test;

import com.jeizas.algorithm.Sort;

/**
 * Created by jeizas on 6/11/17.
 */
public class SortTest {

    public static void main(String[] args) {
        Sort sort = new Sort(new int[]{1,3,6,4,9,5,1});
        sort.insertSort().output();
    }
}
