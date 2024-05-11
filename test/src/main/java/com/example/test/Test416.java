package com.example.test;

public class Test416 {

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
        boolean a = true;
        boolean b = false;
        boolean c = true;
        System.out.println(a | b);
        System.out.println(a & b);
        System.out.println(a && b);
        System.out.println(a || b);
        System.out.println(a || c);
    }

    /**
     * 416. 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * 示例 1：
     * 输入：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
     * 示例 2：
     * 输入：nums = [1,2,3,5]
     * 输出：false
     * 解释：数组不能分割成两个元素和相等的子集。
     * <p>
     */
    public boolean canPartition(int[] nums) {
        int length = nums.length;
        if (length < 2) return false;
        int sum = 0;
        int max = 0;
        for (int num : nums) {
            sum += num;
            max = Math.max(max, num);
        }
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        if (max > target) return false;
        boolean[][] dp = new boolean[length][target + 1];
        for (int i = 0; i < length; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;
        for (int i = 1; i < length; i++) {
            //nums中查找和为j的组合  在他们当中是否能够选出一些数，使得这些数之和恰好为整数 j
            for (int j = 1; j <= target; j++) {
                if (j < nums[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j - nums[i]] || dp[i - 1][j];
                }
            }
        }
        return dp[length - 1][target];
    }

    //nums = [2,5,10,5]
    public boolean canPartition2(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++) {
            for (int j = target; j >= nums[i]; --j) {
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[target];
    }


}
