package com.example.test.leetcode;

import java.util.Arrays;

public class Test31 {

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
     * 31. 下一个排列
     * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
     * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
     * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，
     * 那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
     * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
     * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
     * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
     * 给你一个整数数组 nums ，找出 nums 的下一个排列。
     * 必须 原地 修改，只允许使用额外常数空间。
     * <p>
     * 从后往前迭代，如果一直在增大，则当前位的值为最大值
     * 当出现一个小于，则该位置为交换位，前面不用变，且此位置后面一位为最大值，设为 min
     * 当存在交换位时，找到交换位后比交换位数值大的最小值位置，两者交换
     * 将交换位后的位置用 sort 排序
     * java如何对数组部分元素进行排序
     * 时间空间都不太好
     * 解决思路：（1）两边扫描
     */

    public void nextPermutation(int[] nums) {
        int length = nums.length;
        int i = length - 2;
        // 后面如果是nums[i] > nums[i + 1] 会导致  i减不到-1  导致错误
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i != -1) {
            int min = i + 1;
            for (int j = min; j < length; j++) {
                //如果不加 [2,3,1] 输出[1,2,3] 预期结果是 [3,1,2]
                if (nums[j] < nums[min] && nums[j] > nums[i]) {
                    min = j;
                }
            }
            int temp = nums[min];
            nums[min] = nums[i];
            nums[i] = temp;
        }
        Arrays.sort(nums, i + 1, length);
    }
}
