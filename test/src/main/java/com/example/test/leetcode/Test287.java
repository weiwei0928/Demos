package com.example.test.leetcode;

import java.util.Arrays;

public class Test287 {

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    static class ListNode {
        ListNode next;
        int val;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
    }

    /**
     * 287. 寻找重复数
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
     * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
     * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
     * <p> 13224
     * 注：nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
     */

    public int findDuplicate(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int value = nums[i];
            int key = value - 1;
            if (nums[key] == value) {
                if (i != key) {
                    return value;
                }
                i++;
            } else {
                swap(nums, i, key);
            }
        }
        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
