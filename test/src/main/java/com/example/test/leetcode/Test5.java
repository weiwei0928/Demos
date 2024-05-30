package com.example.test.leetcode;

public class Test5 {

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

        public String longestPalindrome(String s) {
            int len = s.length();
            int left = 0, right = 0, res = 0;//记录左右边界和右-左的长度
            boolean[][] dp = new boolean[len][len];
            for (int i = len - 1; i >= 0; i--) {
                for (int j = i; j < len; j++) {//j=i这步就将每个单个字符dp赋为了true
                    if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {//如果j - i <= 1，必是回文串
                        dp[i][j] = true;
                        if (j - i > res) {
                            res = j - i;//res记得也得更新！
                            left = i;
                            right = j;
                        }
                    }
                }
            }
            return s.substring(left, right + 1);//左闭右开
        }
    }

    public static void main(String[] args) {

    }
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            char c1 = text1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = text2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 5. 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     */
    public String longestPalindrome(String s) {
        int len = s.length();
        int l = 0, r = 0;
        boolean[][] dp = new boolean[len][len];
        for (int j = 0; j < len; j++) {
            for (int i = 0; i <= j; i++) {
                //一开始要将每个字符 dp赋为了true
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1]);
                if (dp[i][j] && j - i > r - l) {
                    l = i;
                    r = j;
                }
            }
        }
        return s.substring(l, r + 1);
    }
}
