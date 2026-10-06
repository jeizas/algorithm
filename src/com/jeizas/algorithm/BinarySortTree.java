package com.jeizas.algorithm;

import com.jeizas.model.TreeNode;

import java.util.Stack;

/**
 * 二叉排序树操作方法
 * Created by jeizas on 2017/4/21.
 */
public class BinarySortTree {

    public TreeNode root;
    public int size;

    /**
     * 二叉树插入节点
     * @param data
     * @return
     */
    public boolean addTreeNode(int data) {

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
     * 查找方法
     * @param node
     * @param key
     * @return
     */
    public static boolean searchForBinarySortTree(TreeNode node, int key) {
        if (node == null) {
            return false;
        }

        if (node.data == key) {
            return true;
        } else if (node.data > key) {
            return searchForBinarySortTree(node.left, key);
        } else {
            return searchForBinarySortTree(node.right, key);
        }
    }

    /**
     * 删除节点
     * @param key
     * @return
     */
    public boolean deleteTreeNode(int key) {
        TreeNode node = root;
        while (node != null && node.data != key) {
            node = node.data > key ? node.left : node.right;
        }
        if (node == null) {
            return false;
        }

        // 有两个孩子：用前驱的值替换当前节点，转为删除前驱，前驱没有右孩子
        if (node.left != null && node.right != null) {
            TreeNode preNode = preNode(node);
            node.data = preNode.data;
            node = preNode;
        }

        // 此时 node 最多只有一个孩子，用孩子顶替 node 在父节点中的位置
        TreeNode child = node.left != null ? node.left : node.right;
        if (child != null) {
            child.parent = node.parent;
        }
        if (node.parent == null) {
            root = child;
        } else if (node.parent.left == node) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }
        size --;
        return true;
    }

    /**
     * 查找指定节点的前驱节点
     * @param node
     * @return
     */
    public static TreeNode preNode(TreeNode node) {
        if(node == null) {
            return null;
        }
        if(node.left!=null)
            return getMax(node.left);
        TreeNode p=node.parent;
        while(p!=null&&p.left==node) {
            node=p;
            p=p.parent;
        }
        return p;
    }

    /**
     * 查找指定节点的最大节点
     * @param root
     * @return
     */
    public static TreeNode getMax(TreeNode root) {
        if(root==null) {
            return null;
        }

        while(root.right!=null) {
            root=root.right;
        }
        return root;
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

    /**
     * 非递归中序遍历
     */
    public static void middleorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {

            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            if (!stack.isEmpty()) {
                node = stack.pop();
                System.out.print(node.data + " ");
                node = node.right;
            }
        }
    }

    /**
     * 中续遍历 非递归
     * @param root
     */
    public static void middleOrderTraversal2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.peek();
                stack.pop();
                System.out.print(node.data + " ");
                node = node.right;
            }
        }
    }

    /**
     * 先序遍历 非递归
     */
    public void nonRecursivePreOrder1() {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                System.out.print(current.data + " ");
                current = current.left;
            } else {
                current = stack.peek();
                stack.pop();
                current = current.right;
            }
        }
    }

    public void nonRecursivePreOrder2() {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                System.out.print(current.data + " ");
                stack.push(current);
                current = current.left;
            }

            if (!stack.isEmpty()) {
                current = stack.pop();
                current = current.right;
            }
        }
    }


    /**
     * 后序遍历 非递归
     */
    public void noRecursivePostOrder() {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        TreeNode lastVisit = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.peek();

            if (current.right == null || current.right == lastVisit) {
                System.out.print(current.data + " ");
                stack.pop();
                lastVisit = current;
                current = null;
            } else {
                current = current.right;
            }

        }
    }

}
