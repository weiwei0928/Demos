package com.example.test.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Test347 {

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
     * 347. 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     */
//    public int[] topKFrequent(int[] nums, int k) {
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int num : nums) {
//            map.put(num, map.getOrDefault(num, 0) + 1);
//        }
//    }
}
