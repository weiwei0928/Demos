package com.example.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    //-5 , -1, 0, 1, 2, 2, 3, 4
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }

    public int test(int[] nums) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num < min) {
                min = num;
            } else if (num - min > max) {
                max = num - min;
            }
        }
        return max;
    }


    // [2,3,1,1,4]  遍历每个位置x，如果x可达，更新最远可达max为max+nums[x]。
    //遍历过程中，如果最远可达大于等于最后一个位置，返回true。
    // [3,2,1,0,4]
    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) return false;
            max = Math.max(max, i + nums[i]);
            if (max >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    public int jump(int[] nums) {
        int max = 0;
        int end = 0;
        int step = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i] + i);
            if (i == end) {
                step++;
                end = max;
            }
        }
        return step;
    }

}
