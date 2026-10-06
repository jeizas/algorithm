package com.jeizas.algorithm;

import com.jeizas.model.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinarySortTreeTest {

    private static final int[] KEYS = {50, 80, 20, 60, 10, 30, 70, 90, 100, 40};

    private static BinarySortTree buildTree(int... keys) {
        BinarySortTree tree = new BinarySortTree();
        for (int key : keys) {
            assertTrue(tree.addTreeNode(key));
        }
        return tree;
    }

    /** 中序遍历收集节点值，同时检查 parent 指针 */
    private static void inOrder(TreeNode node, TreeNode parent, List<Integer> out) {
        if (node == null) {
            return;
        }
        assertSame(parent, node.parent, "parent of " + node.data);
        inOrder(node.left, node, out);
        out.add(node.data);
        inOrder(node.right, node, out);
    }

    /** 校验树结构，并确认与期望的有序集合一致 */
    private static void assertTree(BinarySortTree tree, TreeSet<Integer> expected) {
        List<Integer> actual = new ArrayList<>();
        inOrder(tree.root, null, actual);
        assertEquals(new ArrayList<>(expected), actual);
        assertEquals(expected.size(), tree.size);
    }

    private static TreeSet<Integer> setOf(int... keys) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int key : keys) {
            set.add(key);
        }
        return set;
    }

    @Test
    void deletesEveryNodeOfSampleTree() {
        for (int key : KEYS) {
            BinarySortTree tree = buildTree(KEYS);
            assertTrue(tree.deleteTreeNode(key), "delete " + key);

            TreeSet<Integer> expected = setOf(KEYS);
            expected.remove(key);
            assertTree(tree, expected);
            assertFalse(BinarySortTree.searchForBinarySortTree(tree.root, key));
        }
    }

    @Test
    void deleteMissingKeyReturnsFalse() {
        BinarySortTree tree = buildTree(KEYS);
        assertFalse(tree.deleteTreeNode(0));
        assertFalse(tree.deleteTreeNode(45));
        assertTree(tree, setOf(KEYS));

        assertFalse(new BinarySortTree().deleteTreeNode(1));
    }

    @Test
    void deletesRootWithZeroOrOneChild() {
        BinarySortTree single = buildTree(5);
        assertTrue(single.deleteTreeNode(5));
        assertNull(single.root);
        assertEquals(0, single.size);

        BinarySortTree leftOnly = buildTree(5, 3, 1);
        assertTrue(leftOnly.deleteTreeNode(5));
        assertEquals(3, leftOnly.root.data);
        assertTree(leftOnly, setOf(3, 1));

        BinarySortTree rightOnly = buildTree(5, 7, 9);
        assertTrue(rightOnly.deleteTreeNode(5));
        assertEquals(7, rightOnly.root.data);
        assertTree(rightOnly, setOf(7, 9));
    }

    @Test
    void rejectsDuplicatesOutsideIntegerCache() {
        BinarySortTree tree = buildTree(200, 1000, -500);
        assertFalse(tree.addTreeNode(200));
        assertFalse(tree.addTreeNode(1000));
        assertFalse(tree.addTreeNode(-500));
        assertTree(tree, setOf(200, 1000, -500));
    }

    @Test
    void randomInsertsAndDeletesMatchTreeSet() {
        Random random = new Random(42);
        for (int round = 0; round < 200; round++) {
            BinarySortTree tree = new BinarySortTree();
            TreeSet<Integer> expected = new TreeSet<>();
            for (int i = 0; i < 100; i++) {
                int key = random.nextInt(60) - 30;
                if (random.nextBoolean()) {
                    // 重复插入由 rejectsDuplicatesOutsideIntegerCache 覆盖，这里跳过以免刷屏
                    if (!expected.contains(key)) {
                        assertTrue(tree.addTreeNode(key));
                        expected.add(key);
                    }
                } else {
                    assertEquals(expected.remove(key), tree.deleteTreeNode(key));
                }
            }
            assertTree(tree, expected);
        }
    }
}
