package com.example.test.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test17 {

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

        public List<String> letterCombinations(String digits) {
            List<String> combinations = new ArrayList<String>();
            if (digits.isEmpty()) {
                return combinations;
            }
            Map<Character, String> phoneMap = new HashMap<Character, String>() {{
                put('2', "abc");
                put('3', "def");
                put('4', "ghi");
                put('5', "jkl");
                put('6', "mno");
                put('7', "pqrs");
                put('8', "tuv");
                put('9', "wxyz");
            }};
            backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
            return combinations;
        }

        public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
            if (index == digits.length()) {
                combinations.add(combination.toString());
            } else {
                char digit = digits.charAt(index);
                String letters = phoneMap.get(digit);
                int lettersCount = letters.length();
                for (int i = 0; i < lettersCount; i++) {
                    combination.append(letters.charAt(i));
                    backtrack(combinations, phoneMap, digits, index + 1, combination);
                    combination.deleteCharAt(index);
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
    }


    /**
     * 17. 电话号码的字母组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 示例 1：
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * 示例 2：
     * 输入：digits = ""
     * 输出：[]
     * 示例 3：
     * 输入：digits = "2"
     * 输出：["a","b","c"]
     */
    public List<String> letterCombinations(String digits) {

        List<String> ans = new ArrayList<>();
        if (digits.isEmpty()) {
            return ans;
        }

        Map<Character, String> map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        backTrack(ans, digits, map, 0, new StringBuilder());
        return ans;
    }

    private void backTrack(List<String> ans, String digits, Map<Character, String> map, int index, StringBuilder sb) {
        if (index == digits.length()) {
            ans.add(sb.toString());
            return;
        }

        char c = digits.charAt(index);
        String s = map.get(c);

        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            backTrack(ans, digits, map, index + 1, sb);
            sb.deleteCharAt(index);
        }
    }


}
