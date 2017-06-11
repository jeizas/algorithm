package com.jeizas.algorithm;

import java.util.Arrays;

/**
 * Created by jeizas on 6/11/17.
 */
public class Sort {

    private Integer[] arr;
    private int len;

    public Sort(Integer[] arr) {
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
     * 冒泡排序
     * 遍历比较相邻的元素，如果反序则交换，直到没有找到反序为止
     * @return
     */
    public Sort bubbleSort() {
        for (int j=0; j<len; j++) {
            for (int i = 1; i < len-j; i++) {
                if (arr[i] < arr[i - 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                }
            }
        }
        return this;
    }

    /**
     * 简单的选择排序
     * 每次遍历找到最小的放到数组第i个位置
     * @return
     */
    public Sort simpleSelectionSort() {
        for (int i=0; i<len; i++) {
            int minIndex = i;
            for (int j=i+1; j<len; j++) {
               if (arr[minIndex] > arr[j]) {
                   minIndex = j;
               }
            }

            if (i != minIndex) {
                swap(i, minIndex);
            }
        }
        return this;
    }

    /**
     * 二分插入排序
     * @return
     */
    public Sort binarySort() {
        for (int i = 1; i < len; i++) {
//            int desIndes = binarySearch(i, arr[i]);
            int desIndes = binarySearch(0, i-1, arr[i], 0);
            int tmp = arr[i];
            for (int j = i; j > desIndes; j--) {
                arr[j] = arr[j-1];
            }
            arr[desIndes] = tmp;
        }
        return this;
    }

    /**
     * 二分查找
     * 非递归
     * @return
     */
    private int binarySearch(int rightIndex, int desValue) {
        if (rightIndex > len || rightIndex < 1)
            return -1;
        int leftIndex = 0;
        int middleIndex = 0;
        while (leftIndex <= rightIndex) {
            middleIndex = (leftIndex + rightIndex) / 2;
            if (desValue < arr[middleIndex]) {
                rightIndex = middleIndex -1;
            } else {
                leftIndex = middleIndex + 1;
            }
        }

        return middleIndex;
    }

    /**
     * 二分查找
     * 递归 结束条件：边界正常结束或边界
     * @return
     */
    public int binarySearch(int leftIndex, int rightIndex, int desValue,int flag) {
        if (desValue < arr[leftIndex]) {
            return leftIndex;
        }
        if (desValue > arr[rightIndex]) {
            return rightIndex + 1;
        }
        //经过上述两个条件判断后，由左边部分得到的desValue一定大于下边对应的值，反之小于
        if (leftIndex == rightIndex) {
            return rightIndex + flag;
        }

        int middleIndex = (leftIndex + rightIndex) / 2;
        if (desValue < arr[middleIndex]) {
            return binarySearch(leftIndex, middleIndex -1, desValue, 1);
        } else {
            return binarySearch(middleIndex +1, rightIndex, desValue, -1);
        }
    }


    /**
     * 交换数组中两下标对应的值
     * @param index1
     * @param index2
     */
    private void swap(int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }


    /**
     * 输出结果
     */
    public void output() {
        Arrays.asList(arr)
                .stream()
                .forEach(a -> System.out.print(a + " "));
    }
}
