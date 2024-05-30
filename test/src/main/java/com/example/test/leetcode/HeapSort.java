package com.example.test.leetcode;

public class HeapSort {
    /**
     * @link <a href="https://www.jb51.net/article/220432.htm">...</a>
     * */
    public static final int[] ARRAY = {1, 4, 2, 5, 0, 6, 3, 7};

    /**
     * 将堆顶元素和尾元素交换交换
     * 后重新调整元素的位置，使之重新变成二叉堆
     * */
    public static int[] heapSort(int[] array) {
        //数组的长度
        int length = array.length;
        if (length < 2) return array;
        //首先构建一个最大堆
        buildMaxHeap(array);

        for (int i : array) {
            System.out.println("array---==="+i);
        }
        //调整为最大堆之后，顶元素为最大元素并与微元素交换
        while (length > 0) {//当lenth <= 0时，说明已经到堆顶
            //交换
            swap(array, 0, length - 1);
            length--;//交换之后相当于把树中的最大值弹出去了，所以要--
            //交换之后从上往下调整使之成为最大堆
            adjustHeap(array, 0, length);
        }
        return array;
    }
    //对元素组构建为一个对应数组的最大堆
    private static void buildMaxHeap(int[] array) {
        //在之前的分析可知，最大堆的构建是从最后一个非叶子节点开始，从下往上，从右往左调整
        //最后一个非叶子节点的位置为：array.length/2 - 1
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            //调整使之成为最大堆
            adjustHeap(array, i, array.length);
        }
    }
    /**
     * 调整
     * @param parent 最后一个非叶子节点
     * @param length 数组的长度
     */
    private static void adjustHeap(int[] array, int parent, int length) {
        //定义最大值的索引
        int maxIndex = parent;
        //parent为对应元素的位置（数组的索引）
        int left = 2 * parent + 1;//左子节点对应元素的位置
        int right = 2 * (parent + 1);//右子节点对应元素的位置
        //判断是否有子节点，再比较父节点和左右子节点的大小
        //因为parent最后一个非叶子节点，所以如果有左右子节点则节点的位置都小于数组的长度
        if (left < length && array[left] > array[maxIndex]) {//左子节点如果比父节点大
            maxIndex = left;
        }
        if (right < length && array[right] > array[maxIndex]) {//右子节点如果比父节点大
            maxIndex = right;
        }
        //maxIndex为父节点，若发生改变则说明不是最大节点，需要交换
        if (maxIndex != parent) {
            swap(array, maxIndex, parent);
            //交换之后递归再次调整比较
            adjustHeap(array, maxIndex, length);
        }
    }
    //交换
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static void print(int[] array) {
        for (int i : array) {
            System.out.print(i + "  ");
        }
    }
    public static void main(String[] args) {
        print(ARRAY);
        System.out.println("============================================");
        print(heapSort(ARRAY));
    }
}

