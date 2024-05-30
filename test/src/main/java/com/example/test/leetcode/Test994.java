package com.example.test.leetcode;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Test994 {

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
        class Solution {
            public int uniquePaths(int m, int n) {
                int[][] f = new int[m][n];
                for (int i = 0; i < m; ++i) {
                    f[i][0] = 1;
                }
                for (int j = 0; j < n; ++j) {
                    f[0][j] = 1;
                }
                for (int i = 1; i < m; ++i) {
                    for (int j = 1; j < n; ++j) {
                        f[i][j] = f[i - 1][j] + f[i][j - 1];
                    }
                }
                return f[m - 1][n - 1];
            }
        }

    }

    /**
     * 994. 腐烂的橘子
     * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
     * 值 0 代表空单元格；
     * 值 1 代表新鲜橘子；
     * 值 2 代表腐烂的橘子。
     * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
     * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
     * ————————————————
     */
    int[] dr = new int[]{-1, 0, 1, 0};
    int[] dc = new int[]{0, -1, 0, 1};

    public int orangesRotting(int[][] grid) {
        int rowNum = grid.length;
        int colNum = grid[0].length;
        int freshCount = 0;
        int min = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 2) {//烂橘子
                    queue.add(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            min++;
            for (int i = 0; i < size; i++) {
                int[] point = queue.poll();
                int x = point[0], y = point[1];
                for (int[] d : directions) {
                    int nextX = x + d[0];
                    int nextY = y + d[1];
                    if (nextX >= 0 && nextY >= 0 && nextX < colNum && nextY < rowNum && grid[nextX][nextY] == 1) {
                        grid[nextX][nextY] = 2;
                        freshCount--;
                        queue.offer(new int[]{nextX, nextY});
                    }
                }
            }
        }
        return freshCount == 0 ? min : -1;
    }

    public int orangesRotting11(int[][] grid) {
        int R = grid.length, C = grid[0].length;
        Queue<Integer> queue = new ArrayDeque<Integer>();
        Map<Integer, Integer> depth = new HashMap<Integer, Integer>();
        for (int r = 0; r < R; ++r) {
            for (int c = 0; c < C; ++c) {
                if (grid[r][c] == 2) {
                    int code = r * C + c;
                    queue.add(code);
                    depth.put(code, 0);
                }
            }
        }
        int ans = 0;
        while (!queue.isEmpty()) {
            int code = queue.remove();
            int r = code / C, c = code % C;
            for (int k = 0; k < 4; ++k) {
                int nr = r + dr[k];
                int nc = c + dc[k];
                if (0 <= nr && nr < R && 0 <= nc && nc < C && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    int ncode = nr * C + nc;
                    queue.add(ncode);
                    depth.put(ncode, depth.get(code) + 1);
                    ans = depth.get(ncode);
                }
            }
        }
        for (int[] row : grid) {
            for (int v : row) {
                if (v == 1) {
                    return -1;
                }
            }
        }
        return ans;
    }


}
