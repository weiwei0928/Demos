package com.example.test.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test208 {

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

    List<List<Integer>> edges;
    int[] visited;
    boolean valid = true;

    public static void main(String[] args) {
    }

    /**
     * 208：实现 Trie (前缀树)
     * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
     * 请你实现 Trie 类：
     * Trie() 初始化前缀树对象。
     * void insert(String word) 向前缀树中插入字符串 word 。
     * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
     * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
     */


    class Trie {

        Map<String, Boolean> map;

        public Trie() {
            map = new HashMap<>();
        }

        public void insert(String word) {
            map.put(word, true);
        }

        public boolean search(String word) {
            return map.getOrDefault(word, false);
        }

        public boolean startsWith(String prefix) {
            int len = prefix.length();
            for (String word : map.keySet()) {
                if (len <= word.length() && word.substring(0, len).endsWith(prefix)) {
                    return true;
                }
            }
            return false;
        }
    }

}











