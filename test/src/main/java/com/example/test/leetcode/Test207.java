package com.example.test.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Test207 {

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

    /**
     * 示例 1：
     * 输入：numCourses = 2, prerequisites = [[1,0]]
     * 输出：true
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
     * 示例 2：
     * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
     * 输出：false  numCourses=8  prerequisites =[[1,0],[1,2]],[3,4]],[2,3]],[4,5]],[6,7]]
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
     */

    public static void main(String[] args) {
    }

    /**
     * 207. 课程表
     * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
     * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
     * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
     * <p>
     * numCourses=8  prerequisites =[[1,0],[1,2]],[3,4]],[2,3]],[4,5]],[6,7]]
     */


    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> edges = new ArrayList<>();//邻接表
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        int[] visited = new int[numCourses];//入度表
        for (int[] prerequisite : prerequisites) {
            edges.get(prerequisite[1]).add(prerequisite[0]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(i,visited,edges))
                return false;
        }
        return true;
    }

    private boolean dfs(int u,int[] visited,List<List<Integer>> edges) {
        if (visited[u] == 2) return true;//被其他节点起始的dfs访问过，被赋值为2说明没有环，所以不需要再dfs直接返回true
        if (visited[u] == 1) return false;// 自己dfs访问到自己  构成环 返回false
        visited[u] = 1; //没有被访问过  赋值为1 表示访问过一次
        for (Integer v : edges.get(u)) {
            if (!dfs(v,visited,edges)){
                return false;//遍历 u的邻接表 对表里每个节点递归  递归到某个没有指向任一节点的节点，
            }
        }
        visited[u] = 2;//如果这个过程中都没有返回 说明没有环，弹栈，把所有递归过的点 置为2
        return true;
    }

}











