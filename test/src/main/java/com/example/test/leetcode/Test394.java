package com.example.test.leetcode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

public class Test394 {

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


        /**
         * *输入：s = "3[a2[c]]"
         * 输出："accaccacc"
         */
        class Solution {
            int ptr;

            public String decodeString(String s) {
                LinkedList<String> stk = new LinkedList<String>();
                ptr = 0;

                while (ptr < s.length()) {
                    char cur = s.charAt(ptr);
                    if (Character.isDigit(cur)) {
                        // 获取一个数字并进栈
                        String digits = getDigits(s);
                        stk.addLast(digits);
                    } else if (Character.isLetter(cur) || cur == '[') {
                        // 获取一个字母并进栈
                        stk.addLast(String.valueOf(s.charAt(ptr++)));
                    } else {
                        ++ptr;
                        LinkedList<String> sub = new LinkedList<String>();
                        while (!"[".equals(stk.peekLast())) {
                            sub.addLast(stk.removeLast());
                        }
                        Collections.reverse(sub);
                        // 左括号出栈
                        stk.removeLast();
                        // 此时栈顶为当前 sub 对应的字符串应该出现的次数
                        int repTime = Integer.parseInt(stk.removeLast());
                        StringBuffer t = new StringBuffer();
                        String o = getString(sub);
                        // 构造字符串
                        while (repTime-- > 0) {
                            t.append(o);
                        }
                        // 将构造好的字符串入栈
                        stk.addLast(t.toString());
                    }
                }

                return getString(stk);
            }

            public String getDigits(String s) {
                StringBuffer ret = new StringBuffer();
                while (Character.isDigit(s.charAt(ptr))) {
                    ret.append(s.charAt(ptr++));
                }
                return ret.toString();
            }

            public String getString(LinkedList<String> v) {
                StringBuffer ret = new StringBuffer();
                for (String s : v) {
                    ret.append(s);
                }
                return ret.toString();
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(decodeString("3[ab]"));
        System.out.println(decodeString("aaabbbccc"));
        System.out.println(decodeString("3[3[b]]"));
        System.out.println(decodeString("3[a]2[bc]"));
        System.out.println(decodeString("11[a]"));
    }

    public static String decodeString2(String s) {
        Stack<String> stack = new Stack<>();
//3[ab]
        for (char c : s.toCharArray()) {
            //如果是右括号
            if (c == ']') {

                StringBuilder eachStr = new StringBuilder();
                //得到[]中的完整字符串
                while (!"[".equals(stack.peek())) {
                    eachStr.insert(0, stack.pop());
                }
                //删除左括号
                stack.pop();
                StringBuilder res = new StringBuilder();
                StringBuilder countSb = new StringBuilder();

                while (!stack.empty()) {
                    if (Character.isDigit(c)) {
                        countSb.insert(0, c);
                    }
                }
                int cnt = Integer.parseInt(countSb.toString());
                while (cnt > 0) {
                    res.append(eachStr);
                    cnt--;
                }
                //栈中压入翻译完的字符串
                stack.push(res.toString());
            } else if (Character.isDigit(c)) {

            } else {
                stack.push(String.valueOf(c));
            }
        }

        StringBuilder res = new StringBuilder();
//        for (String ss : stack) {
//            res.append(ss);
//        }
        while (!stack.isEmpty()) {
            res.insert(0, stack.pop());
        }
        return res.toString();
    }

    // 复制 cnt 次 str
//    public static String process(String str, int cnt) {
//        StringBuilder res = new StringBuilder();
//        while (cnt > 0) {
//            res.append(str);
//            cnt--;
//        }
//        return res.toString();
//    }

    /**
     * 394. 字符串解码
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     * <p>
     * 示例 1：
     * <p>
     * 输入：s = "3[a]2[bc]"
     * 输出："aaabcbc"
     * 示例 2：
     * <p>
     * 输入：s = "3[a2[c]]"
     * 输出："accaccacc"
     */

    static int ptr = 0;

    public static String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        while (ptr < s.length()) {
            char c = s.charAt(ptr);
            if (c == ']') {
                ptr++;
                System.out.println(stack.peek()+"----");
                StringBuilder each = new StringBuilder();
                while (!stack.peek().equals("[")) {
                    each.insert(0, stack.pop());
                }
                stack.pop();//左括号出栈
                int num = Integer.parseInt(stack.pop());
                StringBuilder stringBuilder = new StringBuilder();
                while (num > 0) {
                    stringBuilder.append(each);
                    num--;
                }
                stack.push(stringBuilder.toString());

            } else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();

                while (Character.isDigit(s.charAt(ptr))) {
                    sb.append(s.charAt(ptr));
                    ptr++;
                }
                stack.push(sb.toString());
            } else {
                stack.push(String.valueOf(c));
                ptr++;
            }

        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.insert(0, stack.pop());
        }
        return res.toString();
    }
}
