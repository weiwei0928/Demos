package com.ww.classloaderdemo;

import android.util.Log;

//改成Test1，防止loadClass时加载自己Test类
public class Test1 {

    private static final String TAG = "Test";

    public void test1() {
        Log.d(TAG, "test1: ");
    }
    public void test2() {
        Log.d(TAG, "test2: ");
    }
}
