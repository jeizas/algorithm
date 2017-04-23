package com.jeizas.model;

/**
 * 二叉树节点数据
 * Created by jeizas on 2017/4/21.
 */
public class TreeNode {

    public Integer data;

    public TreeNode parent;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Integer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                '}';
    }
}
