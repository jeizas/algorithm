package com.jeizas.test;

import com.jeizas.algorithm.BinarySortTree;

/**
 * 二叉树的测试类
 * Created by jeizas on 2017/4/21.
 */
public class BinarySortTreeTest {

    public static void main(String[] args) {
        BinarySortTree binarySortTree = new BinarySortTree();

        binarySortTree.addTreeNode(50);
        binarySortTree.addTreeNode(80);
        binarySortTree.addTreeNode(20);
        binarySortTree.addTreeNode(60);
        binarySortTree.addTreeNode(10);
        binarySortTree.addTreeNode(30);
        binarySortTree.addTreeNode(70);
        binarySortTree.addTreeNode(90);
        binarySortTree.addTreeNode(100);
        binarySortTree.addTreeNode(40);

        BinarySortTree.medOrderMethodOne(binarySortTree.root);

        boolean isExit = BinarySortTree.searchForBinarySortTree(binarySortTree.root, 31);
        System.out.println(isExit);

        BinarySortTree.deleteForBinarySortTree(binarySortTree.root, 0);
        BinarySortTree.medOrderMethodOne(binarySortTree.root);

    }
}
