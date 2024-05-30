package com.example.test.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test22 {

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


        class Solution2 {
            public List<String> generateParenthesis(int n) {
                List<String> combinations = new ArrayList<String>();
                generateAll(new char[2 * n], 0, combinations);
                return combinations;
            }

            public void generateAll(char[] current, int pos, List<String> result) {
                if (pos == current.length) {
                    if (valid(current)) {
                        result.add(new String(current));
                    }
                } else {
                    current[pos] = '(';
                    generateAll(current, pos + 1, result);
                    current[pos] = ')';
                    generateAll(current, pos + 1, result);
                }
            }

            public boolean valid(char[] current) {
                int balance = 0;
                for (char c : current) {
                    if (c == '(') {
                        ++balance;
                    } else {
                        --balance;
                    }
                    if (balance < 0) {
                        return false;
                    }
                }
                return balance == 0;
            }
        }

    }

//    public static List<String> generateParenthesis3(int n) {
//        List<String> ans = new ArrayList<String>();
//        backtrack(ans, new StringBuilder(), 0, 0, n);
//        return ans;
//    }

    //    public static void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
//        if (cur.length() == max * 2) {
//            ans.add(cur.toString());
//            return;
//        }
//        if (open < max) {
//            cur.append('(');
//            System.out.println(cur);
//            backtrack(ans, cur, open + 1, close, max);
//            cur.deleteCharAt(cur.length() - 1);
//            System.out.println(cur);
//        }
//        if (close < open) {
//            cur.append(')');
//            System.out.println(cur);
//            backtrack(ans, cur, open, close + 1, max);
//            cur.deleteCharAt(cur.length() - 1);
//            System.out.println(cur);
//        }
//
//
//    }
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
//        generateParenthesis3(3);
    }


    /**
     * 22. 括号生成
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * 示例 1：
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     * 示例 2：
     * 输入：n = 1
     * 输出：["()"]
     */
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    private void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == 2 * max) {
            ans.add(cur.toString());
            return;
        }
        if (open < max) {
            cur.append("(");
            backtrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (close < open) {
            cur.append(")");
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }

}
