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
                for (; j >= 0 && tmp < arr[j]; j--) {
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
            boolean swapped = false;
            for (int i = 1; i < len-j; i++) {
                if (arr[i] < arr[i - 1]) {
                    swap(i, i - 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
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
            int desIndes = binarySearch(0, i-1, arr[i]);
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
     * 递归 在有序区间 arr[leftIndex..rightIndex] 中查找 desValue 的插入位置，
     * 相等时插到相等元素之后，保证排序稳定
     * @return
     */
    public int binarySearch(int leftIndex, int rightIndex, int desValue) {
        if (desValue < arr[leftIndex]) {
            return leftIndex;
        }
        if (desValue > arr[rightIndex]) {
            return rightIndex + 1;
        }
        //经过上述两个条件判断后，arr[leftIndex] <= desValue <= arr[rightIndex]，只剩一个元素时它一定等于 desValue
        if (leftIndex == rightIndex) {
            return rightIndex + 1;
        }

        int middleIndex = (leftIndex + rightIndex) / 2;
        if (desValue < arr[middleIndex]) {
            return binarySearch(leftIndex, middleIndex -1, desValue);
        } else {
            return binarySearch(middleIndex +1, rightIndex, desValue);
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
