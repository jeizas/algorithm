package com.jeizas.algorithm;

import com.jeizas.model.TreeNode;

/**
 * 二叉排序树操作方法
 * Created by jeizas on 2017/4/21.
 */
public class BinarySortTree {

    public TreeNode root;
    public int size;

    /**
     * 二叉树中国插入节点
     * @param data
     * @return
     */
    public boolean addTreeNode(Integer data) {

        TreeNode treeNode = new TreeNode(data);

        if (root == null) {
            root = treeNode;
            size = 1;
            return true;
        }

        TreeNode currentNode = root;
        TreeNode parentNode;

        while (true) {
            if (currentNode.data == data) {
                System.out.println("数据已经存在了...");
                return false;
            }

            parentNode = currentNode;

            if (currentNode.data > data) {
                currentNode = parentNode.left;
                if (null == currentNode) {
                    parentNode.left = treeNode;
                    treeNode.parent = parentNode;
                    size ++;
                    return true;
                }
            } else {
                currentNode = parentNode.right;
                if (null == currentNode) {
                    parentNode.right = treeNode;
                    treeNode.parent = parentNode;
                    size ++;
                    return true;
                }
            }
        }
    }

    /**
     * 递归实现中序遍历
     * @author linbingwen
     * @since  2015年8月29日
     * @param treeNode
     */
    public static void medOrderMethodOne(TreeNode treeNode){
        if (null != treeNode) {
            if (null != treeNode.left) {
                medOrderMethodOne(treeNode.left);
            }
            System.out.print(treeNode.data + "  ");
            if (null != treeNode.right) {
                medOrderMethodOne(treeNode.right);
            }
        }
    }
}
