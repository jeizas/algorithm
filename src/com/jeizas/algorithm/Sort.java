package com.jeizas.algorithm;

/**
 * Created by jeizas on 6/11/17.
 */
public class Sort {

    private int[] arr;
    private int len;

    public Sort(int[] arr) {
        this.arr = arr;
        this.len = arr.length;
    }

    /**
     * 插入排序
     * 间断后移，将值插入
     */
    public Sort insertSort() {
        for (int i = 1; i < len; i++) {
            if (arr[i] < arr[i-1]) {
                int tmp = arr[i];
                int j = i - 1;
                for (; j > 0 && tmp < arr[j]; j--) {
                    arr[j+1] = arr[j];
                }
                arr[j+1] = tmp;
            }
        }
        return this;
    }



    /**
     * 输出结果
     */
    public void output() {
        for (int a : arr) {
            System.out.println(a);
        }
    }
}
